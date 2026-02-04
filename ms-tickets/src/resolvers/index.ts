import { ticketQueries } from './queries/ticket.query';
import { ticketMutations } from './mutations/ticket.mutation';

/**
 * Resolvers combinados para el servidor GraphQL.
 * Agrupa todas las consultas y mutaciones en un solo objeto.
 */
export const ticketResolvers = {
  Query: {
    ...ticketQueries,
  },
  Mutation: {
    ...ticketMutations,
  },
  Ticket: {
    // Mapear fechaEntrada (entidad) a fechaIngreso (schema GraphQL)
    fechaIngreso: (parent: any) => parent.fechaEntrada,

    // Mapear tiempoEstacionado (entidad) a tiempoEstacionadoMinutos (schema GraphQL)
    tiempoEstacionadoMinutos: (parent: any) => parent.tiempoEstacionado,
  },
};