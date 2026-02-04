import { FetchClient } from "../utils/fetchClient";
import { ZoneResponse, SpaceResponse } from "../interfaces/zone.interface";

export class ZoneService {
  private baseUrl: string;

  constructor() {
    this.baseUrl = process.env.ZONE_SERVICE_URL || "http://localhost:8080/api";

    if (!this.baseUrl) {
      throw new Error("Cannot start ZoneService: ZONE_SERVICE_URL is not defined");
    }
  }

  getAllZones = async (): Promise<ZoneResponse[]> => {
    const url = `${this.baseUrl}/zones`;
    return await FetchClient.get<ZoneResponse[]>(url);
  };

  getAvailableSpaces = async (): Promise<SpaceResponse[]> => {
    const url = `${this.baseUrl}/spaces/availables`;
    return await FetchClient.get<SpaceResponse[]>(url);
  };

  getAvailableSpacesByZone = async (zoneId: string): Promise<SpaceResponse[]> => {
    const url = `${this.baseUrl}/spaces/availablesbyzone/${zoneId}`;
    return await FetchClient.get<SpaceResponse[]>(url);
  };

  getSpaceById = async (spaceId: string): Promise<SpaceResponse> => {
    const url = `${this.baseUrl}/spaces/${spaceId}`;
    return await FetchClient.get<SpaceResponse>(url);
  };
  /**
 * Obtiene una zona específica por su ID.
 * @param zoneId - ID de la zona a buscar
 * @returns Promise con la zona encontrada
 */
  getZoneById = async (zoneId: string): Promise<ZoneResponse> => {
    const url = `${this.baseUrl}/zones/${zoneId}`;
    return await FetchClient.get<ZoneResponse>(url);
  };
  /**
   * Actualiza el estado de un espacio (por ejemplo, marcarlo como ocupado).
   * @param spaceId - ID del espacio a actualizar
   * @param status - Nuevo estado (AVAILABLE, OCCUPIED, MAINTENANCE)
   * @returns Promise con el espacio actualizado
   */
  updateSpaceStatus = async (spaceId: string, status: string): Promise<SpaceResponse> => {
    const url = `${this.baseUrl}/spaces/${spaceId}/status?status=${status}`;
    return await FetchClient.patch<SpaceResponse>(url, {});
  };
  /**
   * Obtiene un espacio específico por su código.
   * @param code - Código del espacio a buscar
   * @returns Promise con el espacio encontrado
   */
  getSpaceByCode = async (code: string): Promise<SpaceResponse> => {
    const url = `${this.baseUrl}/spaces`;
    const allSpaces = await FetchClient.get<SpaceResponse[]>(url);

    const space = allSpaces.find(s => s.code === code);
    if (!space) {
      throw new Error(`Espacio con código ${code} no encontrado`);
    }

    return space;
  };
}