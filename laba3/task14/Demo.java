package task14;

public class Demo {
    public static void main(String[] args) {
        // Сценарий 1: без обращений вытесняется самый старый
        LruCache<String, Integer> cache = new LruCache<>(3);
        cache.put("a", 1);
        cache.put("b", 2);
        cache.put("c", 3);
        System.out.println("1. Начало:        " + cache);
        cache.put("d", 4);
        System.out.println("   После put(d):  " + cache);

        // Сценарий 2: get обновляет недавность, вытесняется уже другой ключ
        cache.get("c");
        System.out.println("2. После get(c):  " + cache);
        cache.put("e", 5);
        System.out.println("   После put(e):  " + cache);

        // Сценарий 3: put существующего ключа обновляет значение и недавность
        cache.put("d", 40);
        System.out.println("3. После put(d):  " + cache + ", d=" + cache.get("d"));

        // Сценарий 4: null-политика
        try {
            cache.put("x", null);
        } catch (NullPointerException e) {
            System.out.println("4. null-значение запрещено: " + e.getMessage());
        }
        System.out.println("   Промах get(zzz) = " + cache.get("zzz"));

        // Сценарий 5: стабильность ключей. У ArrayList хеш зависит от содержимого, поэтому после изменения ключ теряется.
        LruCache<java.util.List<String>, Integer> unsafe = new LruCache<>(2);
        java.util.List<String> key = new java.util.ArrayList<>(java.util.List.of("k"));
        unsafe.put(key, 1);
        System.out.println("5. Изменяемый ключ до изменения: get = " + unsafe.get(key));
        key.add("!");
        System.out.println("   после изменения ключа:        get = " + unsafe.get(key) + ", а запись в кэше есть: " + unsafe);
    }
}
