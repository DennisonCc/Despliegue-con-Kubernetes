import { Module } from '@nestjs/common';
import { RabbitMQService } from './rabbitmq.service';
import { NotificationModule } from '../notifications/notification.module';

@Module({
  imports: [NotificationModule],
  providers: [RabbitMQService],
  exports: [RabbitMQService],
})
export class RabbitMQModule {}