import model.Transaction;

import java.util.Comparator;

public class TransactionComparator implements Comparator<Transaction> {

    public int compare(Transaction o1, Transaction o2) {
        if (o1.getTradeId().equals(o2.getTradeId())) {
            if (o1.getVersion().compareTo(o2.getVersion()) > 0) {
                return 1;
            } else {
                return -1;
            }
        } else if (o1.getTradeId().compareTo(o2.getTradeId()) > 0) {
            return 1;
        } else {
            return -1;
        }
    }
}
