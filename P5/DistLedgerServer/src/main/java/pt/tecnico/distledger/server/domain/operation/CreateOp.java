package pt.tecnico.distledger.server.domain.operation;
import static pt.ulisboa.tecnico.distledger.contract.DistLedgerCommonDefinitions.OperationType.OP_CREATE_ACCOUNT;

public class CreateOp extends Operation {

    public CreateOp(String account) {
        super(account);
        this.code = OP_CREATE_ACCOUNT;
    }

}
