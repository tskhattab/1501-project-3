package cs1501_p3;

public class CustomHashMap<K, V>
{
    private class Node<K, V>
    {
        K key;
        V value;
        Node <K, V> next;

        public Node(K key, V value)
        {
            this.key = key;
            this.value = value;
        }
    }

    private Node<K, V> [] array;
    private int size;
    private int capacity;

    public CustomHashMap(int capacity)
    {
        this.capacity = capacity;
        array = new Node[capacity];
    }

    private int hash(K key) 
    {
        return Math.abs(key.hashCode()) % capacity;
    }

    public boolean contains(K key) 
    {
        int index = hash(key);
        Node<K, V> list = array[index];

        while (list != null) 
        {
            if (list.key.equals(key)) 
            {
                return true;
            }
            list = list.next;
        }
        return false;
    }

    public V get(K key) 
    {
        int index = hash(key);
        Node<K, V> list = array[index];

        while (list != null) 
        {
            if (list.key.equals(key))
            {
                return list.value;
            }
            list = list.next;
        }
        return null;
    }

    public void put(K key, V value) 
    {
        int index = hash(key);
        Node<K, V> list = array[index];

        if (list == null) 
        {
            array[index] = new Node<K, V>(key, value);
        } 
        else 
        {
            boolean done = false;

            while (list.next != null) 
            {
                if (list.key.equals(key)) 
                {
                    list.value = value;
                    done = true;
                    break;
                }
                list = list.next;
            }

            if (!done)
            {
                list.next = new Node<K, V>(key, value);
            }
        }
    }

    public boolean remove(K key) 
    {
        int index = hash(key);
        Node<K, V> list = array[index];
        Node<K, V> prev = null;

        while (list != null) 
        {
            if (list.key.equals(key)) 
            {
                if (prev == null)
                {
                    array[index] = list.next;
                }
                else
                {
                    prev.next = list.next;
                }
                return true;
            }

            prev = list;
            list = list.next;
        }

        return false;
    }
}
