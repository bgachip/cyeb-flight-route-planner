# Flight Route Planner

## Run with Docker

From the project root run:

```bash
docker compose up --build
```

After the containers have started, the application is available at:

http://localhost:4200

The backend API runs at:

http://localhost:8080

## Run without Docker

Start the backend:

```bash
cd backend
mvnw.cmd spring-boot:run
```

Start the frontend in a separate terminal:

```bash
cd frontend
npm install
npm start
```

Then open:

http://localhost:4200