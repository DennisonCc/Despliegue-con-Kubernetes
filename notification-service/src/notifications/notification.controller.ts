import { Controller, Get } from "@nestjs/common";
import {ApiOperation, ApiResponse, ApiTags } from "@nestjs/swagger";
import { NotificationService } from "./notification.service";
import { NotificationResponseDto } from "./dto/notification-response.dto";

@ApiTags('notifications')
@Controller('notifications')
export class NotificationController{
    constructor(private readonly notificationService: NotificationService){}

    @Get()
    @ApiOperation({
        summary: 'Get all notifications',
        description: 'Returns all notifications',
    })
    @ApiResponse({
        status: 200,
        description: 'List all notifications',
        type: [NotificationResponseDto],
    })
    async findAll(){
        return await this.notificationService.findAll();
    }
}