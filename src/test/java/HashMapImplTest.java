import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class HashMapImplTest {

    private static final int KEY = 1;
    private static final long VALUE = 2l;
    private static final int SECOND_KEY = 1;
    private static final long SECOND_VALUE = 2l;
    private static final int EXPECTED_MAP_SIZE = 1;

    private Map testedInstance;

    @Before
    public void setUp() {
        testedInstance = new HashMapImpl();
    }

    @Test
    public void shouldReturnSizeWhenMapHasElement() {
        testedInstance.put(KEY, VALUE);
        int actualSize = testedInstance.size();
        assertEquals(EXPECTED_MAP_SIZE, actualSize);
    }

    @Test
    public void shouldGetValueByKey() {
        testedInstance.put(KEY, VALUE);
        long actualValue = testedInstance.get(KEY);
        assertEquals(VALUE, actualValue);
    }

    @Test
    public void shouldReturnMinIntegerWhenEntryNotExists() {
        long actualValue = testedInstance.get(KEY);
        assertEquals(Long.MIN_VALUE, actualValue);
    }

    @Test
    public void shouldResizeMap() {
        testedInstance = new HashMapImpl(1);
        testedInstance.put(KEY, VALUE);
        testedInstance.put(SECOND_KEY, SECOND_VALUE);
        long actualValue = testedInstance.get(SECOND_KEY);
        assertEquals(SECOND_VALUE, actualValue);
    }
}