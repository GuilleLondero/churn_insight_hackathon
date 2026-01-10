package com.churnInsight.churnInsight.service;

import com.churnInsight.churnInsight.client.DsClient;
import com.churnInsight.churnInsight.domain.dto.PredictRequest;
import com.churnInsight.churnInsight.domain.dto.PredictResponse;
import com.churnInsight.churnInsight.domain.dto.requestToDSDTO.PredictRequestToDS;
import com.churnInsight.churnInsight.entity.PredictionLog;
import com.churnInsight.churnInsight.exception.DsServiceException;
import com.churnInsight.churnInsight.repository.PredictionLogRepository;

import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.server.MethodNotAllowedException;

import java.time.Instant;
@Service
@Validated
public class PredictionService {

    private final PredictionLogRepository predictionLogRepository;
    private final DsClient dsClient;
    private final DataConverter dataConverter;

    public PredictionService(PredictionLogRepository predictionLogRepository,
                             DsClient dsClient) {
        this.predictionLogRepository = predictionLogRepository;
        this.dsClient = dsClient;
        this.dataConverter = new DataConverter();
    }

    public PredictResponse predict(@Valid PredictRequest request, @NotBlank String nombreUsuario) {


        try {
            PredictRequestToDS requestToDS = new PredictRequestToDS(request);
            // 1) Llamar a Data Science (FastAPI)
            String dsResponse = dsClient.predict(requestToDS);
            // 2) Convertir respuesta DS -> PredictResponse
            PredictResponse response =
                    dataConverter.obtenerDatos(dsResponse, PredictResponse.class);// (prediccion, probabilidad, topFeatures);
            // 3) Guardar log OK en Postgres
            
            predictionLogRepository.save(crearLog(response, nombreUsuario, null));
            
            return response;

        }  catch (DsServiceException ex) {
            // Log ERROR + re-lanzar para que GlobalExceptionHandler devuelva 503
            predictionLogRepository.save(crearLog(null, nombreUsuario, ex));

            throw ex;
        }  catch (MethodNotAllowedException ex) {
            // Log ERROR + re-lanzar para que GlobalExceptionHandler devuelva 503
            predictionLogRepository.save(crearLog(null, nombreUsuario, ex));

            throw ex;
        }   catch (ConstraintViolationException ex) {
            // Log ERROR + re-lanzar para que GlobalExceptionHandler devuelva 503
            predictionLogRepository.save(crearLog(null, nombreUsuario, ex));

            throw ex;
        }
    }

    private PredictionLog crearLog(PredictResponse response, String usuario, Exception ex){
        PredictionLog log = new PredictionLog();
        log.setTimestamp(Instant.now());

        if (response != null) {//si se recibe respuesta se genera log valido
            log.setPrediccion(response.getPrediccion().name());
            log.setProbabilidadChurn(response.getProbabilidadChurn());
            log.setModeloVersion(response.getModeloVersion());
            log.setStatus("OK");
            log.setErrorMessage(null);
        // El requisito clave: Guardar el usuario
            log.setUsuario(usuario);
             
        }else{//si hay algun error falla y genera log de error
            log.setPrediccion("Predicción no disponible");
            log.setProbabilidadChurn(0F);
            log.setModeloVersion("ds-error");
            log.setStatus("ERROR");
            log.setErrorMessage("DS no disponible: " + ex.getMessage());
            log.setUsuario(usuario);
        }


        return log;
    }
}