# k8s-manifests Documentation

This project contains Kubernetes manifests for deploying the `app-publicaciones` application, which consists of several microservices and infrastructure components. Below are the details and instructions for deploying the application.

## Project Structure

- **00-namespace.yaml**: Defines a Kubernetes Namespace called `app-publicaciones` to isolate application resources.
  
- **01-infra**: Contains infrastructure configurations.
  - **database-postgres.yaml**: Deployment and Service configuration for PostgreSQL.
  - **rabbitmq.yaml**: Deployment and Service configuration for RabbitMQ.

- **02-apps**: Contains configurations for the microservices.
  - **mc-clientes.yaml**: Deployment and Service for the `mc-clientes` microservice.
  - **ms-tickets.yaml**: Deployment and Service for the `ms-tickets` microservice.
  - **notification-service.yaml**: Deployment and Service for the `notification-service`.
  - **zone-core.yaml**: Deployment and Service for the `zone-core` microservice.

- **03-ingress.yaml**: Defines an Ingress resource to manage HTTP access to the application services.

## Deployment Steps

1. **Install Minikube and kubectl**: Ensure that Minikube and kubectl are installed and configured on your machine.

2. **Start Minikube**: Open a terminal and run:
   ```
   minikube start --driver=docker
   ```

3. **Create the Namespace**: Apply the namespace file:
   ```
   kubectl apply -f k8s-manifests/00-namespace.yaml
   ```

4. **Deploy Infrastructure**: Apply the infrastructure files for PostgreSQL and RabbitMQ:
   ```
   kubectl apply -f k8s-manifests/01-infra/database-postgres.yaml
   kubectl apply -f k8s-manifests/01-infra/rabbitmq.yaml
   ```

5. **Deploy Microservices**: Apply the microservice files:
   ```
   kubectl apply -f k8s-manifests/02-apps/mc-clientes.yaml
   kubectl apply -f k8s-manifests/02-apps/ms-tickets.yaml
   kubectl apply -f k8s-manifests/02-apps/notification-service.yaml
   kubectl apply -f k8s-manifests/02-apps/zone-core.yaml
   ```

6. **Configure Ingress**: Apply the Ingress file:
   ```
   kubectl apply -f k8s-manifests/03-ingress.yaml
   ```

7. **Check Pod Status**: Ensure all pods are in `Running` state:
   ```
   kubectl get pods -n app-publicaciones
   ```

8. **Update Hosts File**: Get the Minikube IP and update your hosts file to point `app-publicaciones.local` to that IP:
   ```
   minikube ip
   ```

   Edit `C:\Windows\System32\drivers\etc\hosts` and add:
   ```
   <MINIKUBE_IP> app-publicaciones.local
   ```

9. **Access the Application**: Open your browser and go to `http://app-publicaciones.local`.

10. **Troubleshoot**: If you encounter issues, check pod logs and service status:
    ```
    kubectl get all -n app-publicaciones
    kubectl logs <pod-name> -n app-publicaciones
    ```

By following these steps, you should be able to deploy and access your application on Kubernetes using Minikube.