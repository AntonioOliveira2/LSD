package pt.tecnico.distledger.server.domain.operation;

import pt.ulisboa.tecnico.distledger.contract.DistLedgerCommonDefinitions;
import static pt.ulisboa.tecnico.distledger.contract.DistLedgerCommonDefinitions.OperationType.OP_UNSPECIFIED;

public class Operation {
    private String account;
    public DistLedgerCommonDefinitions.OperationType code;

    public Operation(String fromAccount) {
        this.account = fromAccount;
        this.code = OP_UNSPECIFIED;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public DistLedgerCommonDefinitions.OperationType getCode() {
        return this.code;
    }

}
