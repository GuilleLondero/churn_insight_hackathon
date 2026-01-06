"""
Funciones auxiliares para extraer TOP features y calcular impacto.
"""

from typing import Dict, List, Any


def get_top_features(
    input_data: Dict[str, Any],
    probabilidad: float,
    top_3_features_names: List[str]
) -> List[Dict[str, Any]]:
    
    importancia_map = {
        'tickets_soporte': 0.1692,
        'tipo_contrato_mensual': 0.1017,
        'tipo_contrato': 0.1017,
        'facturas_impagas': 0.0854,
        'canal_adquisicion_referido': 0.0539,
        'canal_adquisicion': 0.0539,
        'plan_premium': 0.0481,
        'plan': 0.0481,
        'antiguedad': 0.0361,
        'friccion_del_servicio': 0.0390,
        'ratio_valor_uso': 0.0467,
        'cliente_problematico': 0.0320,
        'early_churn_risk': 0.0352,
        'premium_mensual': 0.0156,
        'metodo_pago_tarjeta_credito': 0.0429,
        'metodo_pago_tarjeta_debito': 0.0411,
        'metodo_pago': 0.0420,
        'cambios_plan': 0.0298,
        'frecuencia_uso': 0.0245
    }
    
    def evaluar_impacto(feature: str, valor: Any, probabilidad: float) -> str:
        
        feature_lower = feature.lower()
        
        if 'tickets_soporte' in feature_lower:
            try:
                val_num = int(valor) if not isinstance(valor, int) else valor
                return "alto_riesgo" if val_num >= 3 else "bajo_riesgo"
            except:
                return "medio_riesgo"
        
        elif 'facturas_impagas' in feature_lower:
            try:
                val_num = int(valor) if not isinstance(valor, int) else valor
                return "alto_riesgo" if val_num > 0 else "bajo_riesgo"
            except:
                return "medio_riesgo"
        
        elif 'antiguedad' in feature_lower:
            try:
                val_num = int(valor) if not isinstance(valor, int) else valor
                return "alto_riesgo" if val_num < 12 else "bajo_riesgo"
            except:
                return "medio_riesgo"
        
        elif 'frecuencia_uso' in feature_lower:
            try:
                val_num = int(valor) if not isinstance(valor, int) else valor
                return "alto_riesgo" if val_num < 15 else "bajo_riesgo"
            except:
                return "medio_riesgo"
        
        elif 'cambios_plan' in feature_lower:
            try:
                val_num = int(valor) if not isinstance(valor, int) else valor
                return "alto_riesgo" if val_num > 1 else "bajo_riesgo"
            except:
                return "medio_riesgo"
        
        elif 'tipo_contrato' in feature_lower:
            tipo_contrato = input_data.get('tipo_contrato', '')
            if 'mensual' in feature_lower or tipo_contrato == 'mensual':
                return "alto_riesgo"
            return "bajo_riesgo"
        
        elif 'plan' in feature_lower:
            plan = input_data.get('plan', '')
            if 'premium' in feature_lower or plan == 'premium':
                return "medio_riesgo"
            elif 'basico' in feature_lower or plan == 'basico':
                return "alto_riesgo"
            return "bajo_riesgo"
        
        elif 'metodo_pago' in feature_lower:
            metodo = input_data.get('metodo_pago', '')
            if 'tarjeta_credito' in feature_lower or metodo == 'tarjeta_credito':
                return "bajo_riesgo"
            return "medio_riesgo"
        
        elif 'canal_adquisicion' in feature_lower:
            canal = input_data.get('canal_adquisicion', '')
            if 'referido' in feature_lower or canal == 'referido':
                return "bajo_riesgo"
            elif 'publicidad' in feature_lower or canal == 'publicidad':
                return "alto_riesgo"
            return "medio_riesgo"
        
        elif any(x in feature_lower for x in ['friccion', 'cliente_problematico', 'early_churn', 'premium_mensual']):
            return "alto_riesgo" if probabilidad > 0.5 else "medio_riesgo"
        
        return "medio_riesgo"
    
    top_features = []
    
    for feature_name in top_3_features_names[:3]:
        
        feature_base = feature_name.replace('_mensual', '').replace('_referido', '').replace('_premium', '').replace('_basico', '').replace('_tarjeta_credito', '').replace('_tarjeta_debito', '').replace('_transferencia_bancaria', '')
        
        valor_cliente = None
        
        for key, val in input_data.items():
            if key in feature_name or feature_name.startswith(key) or key in feature_base:
                valor_cliente = str(val)
                break
        
        if valor_cliente is None:
            if '_' in feature_name:
                valor_cliente = feature_name.split('_')[-1]
            else:
                valor_cliente = "N/A"
        
        impacto = evaluar_impacto(feature_name, valor_cliente, probabilidad)
        
        importancia = importancia_map.get(feature_name, 0.05)
        
        top_features.append({
            "feature": feature_name,
            "valor_cliente": valor_cliente,
            "impacto": impacto,
            "importancia": round(importancia, 4)
        })
    
    return top_features