import { FetchClient } from "../utils/fetchClient";
import { PersonaResponse, VehiculoResponse } from "../interfaces/persona.interface";

export class PersonaService {
    private baseUrl: string;

    constructor() {
        this.baseUrl = process.env.PERSONA_SERVICE_URL || "http://localhost:8081/api";
        if (!this.baseUrl) {
            throw new Error("Cannot start PersonaService: PERSONA_SERVICE_URL is not defined");
        }
    }

    getPersonaByIdentificacion = async (identificacion: string): Promise<PersonaResponse> => {
        const url = `${this.baseUrl}/personas/identificacion/${identificacion}`;
        return await FetchClient.get<PersonaResponse>(url);
    };

    getVehiculoByPlaca = async (placa: string): Promise<VehiculoResponse> => {
        const url = `${this.baseUrl}/vehiculos/placa/${placa}`;
        return await FetchClient.get<VehiculoResponse>(url);
    };
    /**
   * Verifica si un vehículo pertenece a una persona específica.
   * @param personaId - ID de la persona
   * @param vehiculoId - ID del vehículo
   * @returns Promise con booleano indicando si pertenece
   */
    validateVehiculoBelongsToPersona = async (personaId: string, vehiculoId: string): Promise<boolean> => {
        const url = `${this.baseUrl}/vehiculos/propietario/${personaId}`;
        const vehiculos = await FetchClient.get<VehiculoResponse[]>(url);

        return vehiculos.some(v => v.id === vehiculoId);
    };
}