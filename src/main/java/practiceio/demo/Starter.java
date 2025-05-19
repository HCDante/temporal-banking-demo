package practiceio.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import io.temporal.client.WorkflowClient;
import io.temporal.client.WorkflowOptions;
import io.temporal.serviceclient.WorkflowServiceStubs;
import io.temporal.worker.Worker;
import io.temporal.worker.WorkerFactory;
import practiceio.demo.payment.PaymentWorkFlowImpl;
import practiceio.demo.payment.PaymentActivitiesImpl;
import practiceio.demo.transaction.TransactionWorkFlowImpl;
import practiceio.demo.transaction.TransactionActivitiesImpl;
import practiceio.demo.orchestration.OrchestrationWorkFlowImpl;

@SpringBootApplication
public class Starter {
    public static final String TASK_QUEUE = "BANK_TASK_QUEUE";

    public static void main(String[] args) {
        SpringApplication.run(Starter.class, args);
    }

    @Bean
    public WorkflowClient workflowClient() {
        WorkflowServiceStubs service = WorkflowServiceStubs.newLocalServiceStubs();
        return WorkflowClient.newInstance(service);
    }

    @Bean
    public WorkerFactory workerFactory(WorkflowClient client) {
        WorkerFactory factory = WorkerFactory.newInstance(client);
        Worker worker = factory.newWorker(TASK_QUEUE);
        // Register workflows
        worker.registerWorkflowImplementationTypes(
            PaymentWorkFlowImpl.class,
            TransactionWorkFlowImpl.class,
            OrchestrationWorkFlowImpl.class
        );
        // Register activities
        worker.registerActivitiesImplementations(
            new PaymentActivitiesImpl(),
            new TransactionActivitiesImpl()
        );
        factory.start();
        return factory;
    }
}
