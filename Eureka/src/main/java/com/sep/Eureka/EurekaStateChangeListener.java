package com.sep.Eureka;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.shared.Applications;
import com.netflix.eureka.EurekaServerContextHolder;
import com.netflix.eureka.registry.PeerAwareInstanceRegistry;
import com.sep.Eureka.service.definition.IPaymentTypeService;
import org.springframework.cloud.netflix.eureka.server.event.*;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * eureka event listener
 * For example: for monitoring eureka service down notification
 *
 * @author hrabbit
 * @date 2018-09-13
 */
@SuppressWarnings({"JavaDoc", "unused"})
@Component
public class EurekaStateChangeListener {

    private final IPaymentTypeService _paymentTypeService;

    public EurekaStateChangeListener(IPaymentTypeService paymentTypeService) {
        _paymentTypeService = paymentTypeService;
    }

    /**
     * EurekaInstanceCanceledEvent service offline event
     * @param event
     */
    @EventListener
    public void listen(EurekaInstanceCanceledEvent event) {
//        System.out.println(event.getServerId() + "\t" + event.getAppName() + " -service offline");
        System.out.println("REMOVED SERVICE: " + event.getAppName());
    }

    /**
     * EurekaInstanceRegisteredEvent service registration event
     * @param event
     */
    @EventListener
    public void listen(EurekaInstanceRegisteredEvent event) {
        InstanceInfo instanceInfo = event.getInstanceInfo();
        if(!instanceInfo.getAppName().equals("EUREKA-SERVICEREGISTRY")) {
            if(!instanceInfo.getAppName().equals("ZUUL")) {
                _paymentTypeService.addPaymentType(instanceInfo.getAppName());
            }
        }
        System.out.println("NEW SERVICE REGISTERED: " + instanceInfo.getAppName());
        Applications applications = getAllApplicationsOnEureka();
        printAllServices(applications);
    }

    /**
     * EurekaInstanceRenewedEvent service renewal event
     * @param event
     */
    @EventListener
    public void listen(EurekaInstanceRenewedEvent event) {
//        System.out.println(event.getServerId() + "\t" + event.getAppName() + " -service Renewal");
    }

    /**
     * EurekaRegistryAvailableEvent Eureka Registration Center Launch Event
     * @param event
     */
    @EventListener
    public void listen(EurekaRegistryAvailableEvent event) {
//        System.out.println("Registration Startup");
    }

    /**
     * EurekaServerStartedEvent Eureka Server startup event
     * @param event
     */
    @EventListener
    public void listen(EurekaServerStartedEvent event) {
//        System.out.println("Eureka Server Startup");
    }

    private void printAllServices(Applications applications) {
        System.out.println("REGISTERED SERVICES: ");
        applications.getRegisteredApplications().forEach((registeredApplication) -> registeredApplication.getInstances().forEach((instance) -> {
            System.out.println("\t" + instance.getAppName() + " (" + instance.getInstanceId() + ")");
        }));
    }

    private Applications getAllApplicationsOnEureka() {
        PeerAwareInstanceRegistry registry = EurekaServerContextHolder.getInstance().getServerContext().getRegistry();
        return registry.getApplications();
    }
}
