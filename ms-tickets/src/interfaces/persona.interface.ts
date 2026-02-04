export interface PersonaResponse {
    id: string;
    identificacion: string; //cedula-ruc
    nombre: string; //nombre completo - razon social (cambiado de 'nombres' a 'nombre' para coincidir con Java)
    email: string;
    telefono: string;
    tipoPersona: string;
    activo: boolean;
}

export interface VehiculoResponse {
    id: string;
    placa: string;
    marca: string;
    modelo: string;
    color: string;
    anioFabricacion: number;
    tipoVehiculo: string;
    propietarioId: string;
    nombrePropietario: string;
    fechaRegistro: string; //string formateado
    impuesto: number;
    activo: boolean;
}
