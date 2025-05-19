package practiceio.demo.api;

import org.springframework.web.bind.annotation.*;
import io.temporal.client.WorkflowClient;
import io.temporal.client.WorkflowOptions;
import practiceio.demo.Starter;
import practiceio.demo.payment.PaymentWorkFlow;
import practiceio.demo.transaction.TransactionWorkFlow;
import practiceio.demo.orchestration.OrchestrationWorkFlow;

@RestController
@RequestMapping("/api")
public class BankingController {

    private final WorkflowClient client;

    public BankingController(WorkflowClient client) {
        this.client = client;
    }

    @PostMapping("/payment")
    public String doPayment(@RequestBody PaymentRequest req) {
        PaymentWorkFlow wf = client.newWorkflowStub(
            PaymentWorkFlow.class,
            WorkflowOptions.newBuilder().setTaskQueue(Starter.TASK_QUEUE).build()
        );
        return wf.processPayment(req.getAccountId(), req.getAmount());
    }

    @PostMapping("/transaction")
    public String doTransaction(@RequestBody TransactionRequest req) {
        TransactionWorkFlow wf = client.newWorkflowStub(
            TransactionWorkFlow.class,
            WorkflowOptions.newBuilder().setTaskQueue(Starter.TASK_QUEUE).build()
        );
        return wf.processTransaction(req.getFromAccount(), req.getToAccount(), req.getAmount());
    }

    @PostMapping("/orchestrate")
    public String doOrchestration(@RequestBody TransactionRequest req) {
        OrchestrationWorkFlow wf = client.newWorkflowStub(
            OrchestrationWorkFlow.class,
            WorkflowOptions.newBuilder().setTaskQueue(Starter.TASK_QUEUE).build()
        );
        return wf.orchestratePayment(req.getFromAccount(), req.getToAccount(), req.getAmount());
    }
}
