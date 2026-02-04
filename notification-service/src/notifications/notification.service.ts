import { Injectable } from "@nestjs/common";
import { InjectRepository } from "@nestjs/typeorm";
import { Repository } from "typeorm";
import { CreateNotificationDto } from "./dto/create-notification.dto";
import { Notification } from "./entity/notification.entity";

@Injectable()
export class NotificationService {
    constructor(
        @InjectRepository(Notification)
        private notificationRepository: Repository<Notification>,
    ){}

    async create(createNotificationDto: CreateNotificationDto): Promise<Notification> {

        const notification = this.notificationRepository.create({
            ...createNotificationDto,
        });
        return await this.notificationRepository.save(notification);
    }

    async findAll(): Promise<Notification[]> {
        return await this.notificationRepository.find();
    }


}