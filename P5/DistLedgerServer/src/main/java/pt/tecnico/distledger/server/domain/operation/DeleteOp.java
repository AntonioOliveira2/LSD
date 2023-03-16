package pt.tecnico.distledger.server.domain.operation;

import static pt.ulisboa.tecnico.distledger.contract.DistLedgerCommonDefinitions.OperationType.OP_DELETE_ACCOUNT;



public class DeleteOp extends Operation {

    public DeleteOp(String account) {
        super(account);
        this.code = OP_DELETE_ACCOUNT;
    }

}
