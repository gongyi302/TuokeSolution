
import model.Transaction;

import java.util.*;

public class TransactionPosition {

    private Map<String, List<Transaction>> transactionBySecurityCode;
    private Map<String, List<Transaction>> updateTransactionBySecurityCode;
    private Map<String, Long> position;

    public TransactionPosition() {
        this.transactionBySecurityCode = new Hashtable<String, List<Transaction>>();
        this.updateTransactionBySecurityCode = new Hashtable<String, List<Transaction>>();
        this.position = new TreeMap<String, Long>(Collections.reverseOrder());
        this.position.put("ITC", 10L);
    }

    public void inputTransaction(Transaction transaction) {
        String securityCode = transaction.getSecurityCode();
        String action =  transaction.getAction();
        if ("UPDATE".equals(action)) {
            List<Transaction> updateList = this.updateTransactionBySecurityCode.get(securityCode);
            if (updateList == null) {
                updateList = new ArrayList<Transaction>();
                updateList.add(transaction);
                this.updateTransactionBySecurityCode.put(securityCode, updateList);
            } else {
                updateList.add(transaction);
            }
            return;
        }

        List<Transaction> list = this.transactionBySecurityCode.get(securityCode);
        if (list == null) {
            list = new ArrayList<Transaction>();
            list.add(transaction);
            this.transactionBySecurityCode.put(securityCode, list);
        } else {
            list.add(transaction);
        }
    }

    public void outputPosition() {
        Iterator<Map.Entry<String,List<Transaction>>> itr = this.transactionBySecurityCode.entrySet().iterator();
        while (itr.hasNext()) {
            Map.Entry<String,List<Transaction>> entry = itr.next();
            String securityCode = entry.getKey();
            List<Transaction> transactionList = entry.getValue();
            Collections.sort(transactionList, new TransactionComparator());
            List<Transaction> updateList = this.updateTransactionBySecurityCode.get(securityCode);
            if (updateList != null) {
                this.updateTransaction(updateList, transactionList);
            }
            this.populatePosition(securityCode, transactionList);
        }
        this.showPosition();
    }

    private void updateTransaction(List<Transaction> updateList, List<Transaction> transactionList) {
        for (Transaction updateTransaction: updateList) {
            for (Transaction transaction: transactionList) {
                if (transaction.getTradeId().equals(updateTransaction.getTradeId())
                        && transaction.getVersion() == updateTransaction.getVersion() - 1) {
                    if ("Buy".equals(updateTransaction.getAction())) {
                        transaction.setAction("Buy");
                    } else if ("Sell".equals(updateTransaction.getAction())) {
                        transaction.setAction("Sell");
                    }
                    transaction.setQuantity(updateTransaction.getQuantity());
                }
            }
        }
    }

    private void populatePosition(String securityCode, List<Transaction> transactionList) {
        Long sumQuantity = 0L;
        for (Transaction transaction: transactionList) {
            if ("Buy".equals(transaction.getBuyOrSell())) {
                sumQuantity += transaction.getQuantity();
            } else if ("Sell".equals(transaction.getBuyOrSell())) {
                sumQuantity += transaction.getQuantity() * -1;
            }
        }
        Long balanceQuantity = this.position.get(securityCode);
        if (balanceQuantity == null) {
            this.position.put(securityCode, sumQuantity);
        } else {
            this.position.put(securityCode, balanceQuantity + sumQuantity);
        }
    }

    private void showPosition() {
        for (Map.Entry<String, Long> entry : this.position.entrySet())
        {
            String key = (String)entry.getKey();
            String value = entry.getValue() > 0? "+" + entry.getValue(): String.valueOf(entry.getValue());
            System.out.printf("%s=%s\n", key, value);
        }
    }
}
