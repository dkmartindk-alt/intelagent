# Models

## ServiceInstance
- instanceId: String
- serviceName: String
- host: String
- port: Integer
- status: String (UP, DOWN, STARTING, OUT_OF_SERVICE)
- ipAddr: String
- vipAddress: String
- secureVipAddress: String
- healthCheckUrl: String
- homePageUrl: String
- statusPageUrl: String
- metadata: Map<String, String>

## RegistrationRequest
- instance: ServiceInstance

## DiscoveryResponse
- applications: List<Application>
- appsHashCode: String

## Application
- name: String
- instances: List<ServiceInstance>

## Implementation Details
The models are implemented as POJOs (Plain Old Java Objects) with standard getters and setters. They are located in the `open.ipaas.eureka.model` package.