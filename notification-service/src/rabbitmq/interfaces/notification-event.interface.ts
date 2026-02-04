export interface NotificationEvent {
    id: string;
    microservice:string;
    action:string;
    entityType:string;
    entityId:string;
    message:string;
    timestamp:string;
    data?: Record<string, any>;
    severity?:string;
    hostname?:string;
    ipAddress?:string;
}