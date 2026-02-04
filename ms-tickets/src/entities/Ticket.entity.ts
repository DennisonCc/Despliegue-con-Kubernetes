import { Column, Entity, PrimaryGeneratedColumn, CreateDateColumn, UpdateDateColumn, BeforeInsert } from 'typeorm';
import { TicketEstado } from '../interfaces/ticket.interface';

@Entity('Tickets')
export class Ticket {
    @PrimaryGeneratedColumn('uuid')
    id!: string;

    @Column({ name: 'codigo_ticket', unique: true })
    codigoTicket!: string;

    @Column({ name: 'persona_identificacion', nullable: false })
    personaIdentificacion!: string;

    @Column({ name: 'persona_nombres', nullable: false })
    personaNombre!: string;

    @Column({ name: 'vehiculo_placa', nullable: false })
    vehiculoPlaca!: string;

    @Column({ name: 'vehiculo_modelo', nullable: false })
    vehiculoModelo!: string;

    @Column({ name: 'vehiculo_marca', nullable: false })
    vehiculoMarca!: string;

    @Column({ name: 'zona_id', nullable: false })
    zonaId!: string;

    @Column({ name: 'zona_nombre', nullable: false })
    zonaNombre!: string;

    @Column({ name: 'espacio_id', nullable: false })
    espacioId!: string;

    @Column({ name: 'espacio_codigo', nullable: false })
    espacioCodigo!: string;

    @Column({ name: 'fecha_ingreso', type: 'timestamp', nullable: false })
    fechaEntrada!: Date;

    @Column({ name: 'fecha_salida', type: 'timestamp', nullable: true })
    fechaSalida!: Date;

    @Column({
        name: 'estado',
        type: 'enum',
        enum: TicketEstado,
        default: TicketEstado.ACTIVO
    })
    estado!: TicketEstado;

    @Column({ name: 'tiempo_estacionado', type: 'int', nullable: true })
    tiempoEstacionado!: number;

    @Column({ name: 'valor_pagado', type: 'decimal', precision: 10, scale: 2, nullable: true })
    valorPagado!: number;

    @CreateDateColumn({ name: 'created_at' })
    createdAt!: Date;

    @UpdateDateColumn({ name: 'updated_at' })
    updatedAt!: Date;

    @BeforeInsert()
    setFechaEntrada() {
        if (!this.fechaEntrada) {
            this.fechaEntrada = new Date();
        }
    }
}