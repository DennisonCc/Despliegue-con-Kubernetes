import { IsString, IsUUID, IsOptional, IsObject, IsDateString, IsIn } from 'class-validator';

export class CreateNotificationDto {
  @IsUUID()
  eventId: string;

  @IsString()
  microservice: string;

  @IsString()
  @IsIn(['CREATE', 'UPDATE', 'DELETE'])
  action: string;

  @IsString()
  entityType: string;

  @IsUUID()
  entityId: string;

  @IsString()
  message: string;

  @IsDateString()
  eventTimestamp: string;

  @IsOptional()
  @IsObject()
  data?: Record<string, any>;

  @IsOptional()
  @IsString()
  @IsIn(['INFO', 'WARNING', 'ERROR'])
  severity?: string;

  @IsOptional()
  @IsString()
  hostname?: string;

  @IsOptional()
  @IsString()
  ipAddress?: string;
}