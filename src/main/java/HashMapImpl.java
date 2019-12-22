public class HashMapImpl implements Map {

    private static final int DEFAULT_CAPACITY = 16;
    private static int CAPACITY;
    private static final float DEFAULT_LOAD_FACTOR = 0.75F;
    private int size = 0;
    private Entry[] table;

    public HashMapImpl() {
        table = new Entry[DEFAULT_CAPACITY];
        CAPACITY = DEFAULT_CAPACITY;
    }

    public HashMapImpl(int capacity) {
        table = new Entry[capacity];
        CAPACITY = capacity;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public long get(int key) {
        int bucketId = getBucket(key);
        return table[bucketId] == null ? Long.MIN_VALUE : table[bucketId].getValue();

    }

    @Override
    public void put(int key, long value) {
        if (isTimeToResize()) {
            resize();
        }
        int bucketId = getBucket(key);
        if (table[bucketId] == null) {
            table[bucketId] = new Entry(key, value);
            size++;
            return;
        } else if (table[bucketId] != null && table[bucketId].getKey() == key) {
            table[bucketId].setValue(value);
        }
    }

    private boolean isTimeToResize() {
        return size + 1 >= CAPACITY * DEFAULT_LOAD_FACTOR;
    }

    private int getBucket(int key) {
        int hash = getHash(key);
        while ((table[hash] != null && table[hash].getKey() != key)) {
            hash = (hash + 1) % CAPACITY;
        }
        return hash;
    }

    private int getHash(int key) {
        return key / CAPACITY % CAPACITY;
    }

    private void resize() {
        CAPACITY = CAPACITY * 2;
        Entry[] entries = getAllEntries();
        table = new Entry[CAPACITY];
        for (int i = 0; i < entries.length; i++) {
            int k = entries[i].getKey();
            long v = entries[i].getValue();
            put(k, v);
        }
    }

    private Entry[] getAllEntries() {
        Entry[] entries = new Entry[size];
        int j = 0;
        for (int i = 0; i < table.length; i++) {
            if (table[i] != null) {
                entries[j] = table[i];
                j++;
            }
        }
        return entries;
    }

    private class Entry {

        private int key;
        private long value;

        Entry(int key, long value) {
            this.key = key;
            this.value = value;
        }

        public long getValue() {
            return value;
        }

        public void setValue(long value) {
            this.value = value;
        }

        public int getKey() {
            return key;
        }
    }
}
