import model.Transaction;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)

class TransactionPositionTest {

    private TransactionPosition tp;
    private Transaction t1, t2, t3, t4, t5, t6;

    @BeforeAll
    void init() {
        tp = new TransactionPosition();

        t1 = new Transaction();
        t1.setTransactionId("1");
        t1.setTradeId("1");
        t1.setVersion(1);
        t1.setSecurityCode("REL");
        t1.setQuantity(50L);
        t1.setAction("INSERT");
        t1.setBuyOrSell("Buy");

        t2 = new Transaction();
        t2.setTransactionId("2");
        t2.setTradeId("2");
        t2.setVersion(1);
        t2.setSecurityCode("ITC");
        t2.setQuantity(40l);
        t2.setAction("INSERT");
        t2.setBuyOrSell("Sell");

        t3 = new Transaction();
        t3.setTransactionId("3");
        t3.setTradeId("3");
        t3.setVersion(1);
        t3.setSecurityCode("INF");
        t3.setQuantity(70l);
        t3.setAction("INSERT");
        t3.setBuyOrSell("Buy");

        t4 = new Transaction();
        t4.setTransactionId("4");
        t4.setTradeId("1");
        t4.setVersion(2);
        t4.setSecurityCode("REL");
        t4.setQuantity(60L);
        t4.setAction("UPDATE");
        t4.setBuyOrSell("Buy");

        t5 = new Transaction();
        t5.setTransactionId("5");
        t5.setTradeId("2");
        t5.setVersion(2);
        t5.setSecurityCode("ITC");
        t5.setQuantity(30l);
        t5.setAction("CANCEL");
        t5.setBuyOrSell("Buy");

        t6 = new Transaction();
        t6.setTransactionId("6");
        t6.setTradeId("4");
        t6.setVersion(1);
        t6.setSecurityCode("INF");
        t6.setQuantity(20l);
        t6.setAction("INSERT");
        t6.setBuyOrSell("Sell");
    }

    @BeforeEach
    void inputTransaction() {
        tp.inputTransaction(t1);
        tp.inputTransaction(t2);
        tp.inputTransaction(t3);
        tp.inputTransaction(t4);
        tp.inputTransaction(t5);
        tp.inputTransaction(t6);
    }

    @Test
    void outputPosition() {
        tp.outputPosition();
    }
}