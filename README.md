# IPaaS Management Plane - Microservices Architecture

This project implements a microservice-based Integration Platform as a Service (IPaaS) management plane with strict security isolation, fine-grained access control, and extensible resource management.

## Architecture Overview

The system consists of 6 core microservices:

1. **GUI MS** - Administrative user interface
2. **Controller MS** - Central orchestrator
3. **ResourceAccessControl MS** - Security gateway
4. **UserGroup MS** - User and group management
5. **ResourceManager MS** - Resource routing layer
6. **FolderResource MS** - Folder-specific operations

## Folder Structure
```
├── architecture/           # Global architecture diagrams and docs
├── code/                 # Individual microservice implementations
│   ├── gui-ms/           # GUI microservice
│   ├── controller-ms/    # Orchestration microservice
│   ├── resourceaccesscontrol-ms/ # Security microservice
│   ├── usergroup-ms/     # User management microservice
│   ├── resourcemanager-ms/ # Resource routing microservice
│   └── folderresource-ms/ # Folder operations microservice
```

## Basic Demo Features
The initial implementation focuses on folder management capabilities:
- Browse through folder hierarchies
- Create new folders
- Delete existing folders
- View folder contents with proper security filtering

## Getting Started

Each microservice is designed to be developed independently. Follow these steps for each service:

1. Navigate to the specific microservice directory
2. Read the local `.rules` file for development instructions
3. Initialize the Spring Boot application using the Spring Initializr API
4. Implement the endpoints and models as specified in the `architecture/` folder

## Design Principles
- **Data Isolation**: Each microservice owns its own data
- **Security First**: Mandatory security gateway for all resource access
- **Extensibility**: Support for additional resource types beyond folders
- **Consistency**: Folders treated as a special type of resource

## Next Steps
Future enhancements include:
- Additional resource types (Workflows, APIs, Connectors)
- Advanced trigger mechanisms
- Enhanced UI with drag-and-drop capabilities
- Audit logging and monitoring