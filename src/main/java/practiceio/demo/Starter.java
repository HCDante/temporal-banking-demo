package practiceio.demo;

import io.temporal.client.WorkflowClient;
import io.temporal.client.WorkflowOptions;
import io.temporal.serviceclient.WorkflowServiceStubs;
import io.temporal.worker.Worker;
import io.temporal.worker.WorkerFactory;
import practiceio.demo.payment.PaymentWorkFlow;
import practiceio.demo.payment.PaymentWorkFlowImpl;
import practiceio.demo.payment.PaymentActivitiesImpl;
import practiceio.demo.transaction.TransactionWorkFlow;
import practiceio.demo.transaction.TransactionWorkFlowImpl;
import practiceio.demo.transaction.TransactionActivitiesImpl;
import practiceio.demo.orchestration.OrchestrationWorkFlow;
import practiceio.demo.orchestration.OrchestrationWorkFlowImpl;

public class Starter {
    public static final String TASK_QUEUE = "BANK_TASK_QUEUE";

    public static void main(String[] args) {
        // 1. Crear gRPC stubs (local dev server)
        WorkflowServiceStubs service = WorkflowServiceStubs.newLocalServiceStubs();

        // 2. Crear WorkflowClient
        WorkflowClient client = WorkflowClient.newInstance(service);

        // 3. Crear WorkerFactory
        WorkerFactory factory = WorkerFactory.newInstance(client);

        // 4. Crear un Worker para la TASK_QUEUE
        Worker worker = factory.newWorker(TASK_QUEUE);

        // 5. Registrar Workflow implementations
        worker.registerWorkflowImplementationTypes(
                PaymentWorkFlowImpl.class,
                TransactionWorkFlowImpl.class,
                OrchestrationWorkFlowImpl.class
        );

        // 6. Registrar Activities implementations
        worker.registerActivitiesImplementations(
                new PaymentActivitiesImpl(),
                new TransactionActivitiesImpl()
        );

        // 7. Iniciar polling de workers
        factory.start();

        // --- Demo de invocaciones sincronizadas ---

        // Payment Workflow
        PaymentWorkFlow paymentWorkflow = client.newWorkflowStub(
                PaymentWorkFlow.class,
                WorkflowOptions.newBuilder().setTaskQueue(TASK_QUEUE).build()
        );
        String paymentResult = paymentWorkflow.processPayment("123", 100.0);
        System.out.println("Payment result: " + paymentResult);

        // Transaction Workflow
        TransactionWorkFlow transactionWorkflow = client.newWorkflowStub(
                TransactionWorkFlow.class,
                WorkflowOptions.newBuilder().setTaskQueue(TASK_QUEUE).build()
        );
        String transactionResult = transactionWorkflow.processTransaction("123", "456", 50.0);
        System.out.println("Transaction result: " + transactionResult);

        // Orchestration Workflow
        OrchestrationWorkFlow orchestrationWorkflow = client.newWorkflowStub(
                OrchestrationWorkFlow.class,
                WorkflowOptions.newBuilder().setTaskQueue(TASK_QUEUE).build()
        );
        String orchestrationResult = orchestrationWorkflow.orchestratePayment("123", "456", 75.0);
        System.out.println("Orchestration result: " + orchestrationResult);

        // 8. Finalizar aplicación
        System.exit(0);
    }
}