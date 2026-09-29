export const API_ENDPOINTS = {
  cities: {
    smallest: '/cities/smallest',
    largest: '/cities/largest',
  },

  airlines: {
    all: '/airlines',
    flights: (airlineId: number) =>
      `/airlines/${airlineId}/flights`,
  },

  flights: {
    all: '/flights',
  },

  routes: {
    shortest: '/routes/shortest',
  },
} as const;
