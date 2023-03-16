package pt.tecnico.distledger.server.domain;

import pt.tecnico.distledger.server.domain.operation.*;
import static pt.ulisboa.tecnico.distledger.contract.DistLedgerCommonDefinitions.OperationType.OP_TRANSFER_TO;

import pt.ulisboa.tecnico.distledger.contract.DistLedgerCommonDefinitions;
import pt.ulisboa.tecnico.distledger.contract.DistLedgerCommonDefinitions.LedgerState;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class ServerState {

    /* TODO: Here should be declared all the server state attributes
         as well as the methods to access and interact with the state. */

    private List<Operation> ledger;
    private HashMap<String, Integer> users;
    private boolean activated = true;

    public final static Integer OP_SUCCESS = 1;
    public final static Integer ERR_NO_USR = -1;
    public final static Integer ERR_DUP_USR = -2;
    public final static Integer ERR_HAS_BAL = -3;
    public final static Integer ERR_DELETE = -4;
    public final static Integer ERR_NO_DEST = -5;
    public final static Integer ERR_NO_BAL = -6;

    public ServerState() {
        this.ledger = new ArrayList<>();
        this.users = new HashMap<String, Integer>();

        users.put("ledger", 1000);
    }

    public Integer balance(String userId) {
        Integer balance = users.get(userId);
        return balance != null ? balance : ERR_NO_USR;
    }

    public int createAccount(String userId) {
        if (users.get(userId) != null) {
            return ERR_DUP_USR;
        } else {
            users.put(userId, 0);
            ledger.add(new CreateOp(userId));
            return OP_SUCCESS;
        }
    }

    public int deleteAccount(String userId) {
        if (users.get(userId) == null)
            return ERR_NO_USR;

        if (balance(userId) > 0) {
            return ERR_HAS_BAL;
        }

        if (users.remove(userId) != null) {
            ledger.add(new DeleteOp(userId));
            return OP_SUCCESS;
        }

        return ERR_DELETE;
    }

    public int transferTo(String userIdFrom, String userIdTo, int amount) {

        Integer balanceFrom, balanceTo;

        if (users.get(userIdFrom) == null)
            return ERR_NO_USR;

        if (users.get(userIdTo) == null)
            return ERR_NO_DEST;

        if ((balanceFrom = balance(userIdFrom)) < amount) {
            return ERR_NO_BAL;
        } else {
            balanceTo = balance(userIdTo);
            users.replace(userIdFrom, balanceFrom - amount);
            users.replace(userIdTo, balanceTo + amount);
            ledger.add(new TransferOp(userIdFrom, userIdTo, amount));
            return OP_SUCCESS;
        }
    }

    public void activate() { activated = true; }

    public void deactivate() { activated = false; }

    public boolean isActive() { return activated; }

    public LedgerState getLedgerState() {
        LedgerState.Builder ledgerState = LedgerState.newBuilder();

        for (Operation operation : ledger) {
            if (operation.getCode() != OP_TRANSFER_TO) {
                ledgerState.addLedger(
                        DistLedgerCommonDefinitions.Operation.newBuilder().setType(
                                operation.getCode()).setUserId(operation.getAccount()).build());
            } else {
                TransferOp transfer = (TransferOp)operation;
                ledgerState.addLedger(DistLedgerCommonDefinitions.Operation.newBuilder().
                        setType(transfer.getCode()).setUserId(transfer.getAccount()).
                        setDestUserId(transfer.getDestAccount()).setAmount(transfer.getAmount()).build());
            }
        }

        return ledgerState.build();
    }
}
