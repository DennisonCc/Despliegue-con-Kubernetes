export interface ZoneResponse {
    id: string;
    name: string;
    description: string;
    capacity: number;
    type: string;
    isActive: boolean;
}

export interface SpaceResponse {
    id: string;
    code: string;
    status: string;
    isReserved: boolean;
    zoneId: string;
    zoneName: string;
    priority: number;
}
