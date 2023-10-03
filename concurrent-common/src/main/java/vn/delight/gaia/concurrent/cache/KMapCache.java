package vn.delight.gaia.concurrent.cache;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Function;

public interface KMapCache<K, V> {

    Flux<Map.Entry<K,V>> getMultiple(Set<K> key);

    /**
     * If the specified key is not already associated
     * with a value, associate it with the given value.
     * <p>
     * Stores value mapped by key with specified time to live.
     * Entry expires after specified time to live.
     * If the map previously contained a mapping for
     * the key, the old value is replaced by the specified value.
     *
     * @param key   - map key
     * @param value - map value
     * @param ttl   - seconds time to live for key\value entry.
     *              If <code>0</code> then stores infinitely.
     * @return previous associated value
     */
    Mono<V> putIfAbsent(K key, V value, long ttl);

    /**
     * If the specified key is not already associated
     * with a value, associate it with the given value.
     * <p>
     * Stores value mapped by key with specified time to live and max idle time.
     * Entry expires when specified time to live or max idle time has expired.
     * <p>
     * If the map previously contained a mapping for
     * the key, the old value is replaced by the specified value.
     *
     * @param key         - map key
     * @param value       - map value
     * @param ttl         - seconds time to live for key\value entry
     *                    If <code>0</code> then time to live doesn't affect entry expiration.
     * @param maxIdleTime - max seconds idle  time for key\value entry.seconds
     *                    If <code>0</code> then max idle time doesn't affect entry expiration.
     *                    <p>
     *                    if <code>maxIdleTime</code> and <code>ttl</code> params are equal to <code>0</code>
     *                    then entry stores infinitely.
     * @return previous associated value
     */
    Mono<V> putIfAbsent(K key, V value, long ttl, long maxIdleTime);

    /**
     * Stores value mapped by key with specified time to live.
     * Entry expires after specified time to live.
     * If the map previously contained a mapping for
     * the key, the old value is replaced by the specified value.
     *
     * @param key   - map key
     * @param value - map value
     * @param ttl   - seconds time to live for key\value entry
     *              If <code>0</code> then stores infinitely.
     * @return previous associated value
     */
    Mono<V> put(K key, V value, long ttl);

    /**
     * Stores value mapped by key with specified time to live and max idle time.
     * Entry expires when specified time to live or max idle time has expired.
     * <p>
     * If the map previously contained a mapping for
     * the key, the old value is replaced by the specified value.
     *
     * @param key         - map key
     * @param value       - map value
     * @param ttl         -seconds time to live for key\value entry.
     *                    If <code>0</code> then time to live doesn't affect entry expiration.
     * @param maxIdleTime - max seconds idle time for key\value entry.
     *                    If <code>0</code> then max idle time doesn't affect entry expiration.
     *                    <p>
     *                    if <code>maxIdleTime</code> and <code>ttl</code> params are equal to <code>0</code>
     *                    then entry stores infinitely.
     * @return previous associated value
     */
    Mono<V> put(K key, V value, long ttl, long maxIdleTime);

    /**
     * Stores value mapped by key with specified time to live.
     * Entry expires after specified time to live.
     * <p>
     * If the map previously contained a mapping for
     * the key, the old value is replaced by the specified value.
     * <p>
     * as it not returns previous value.
     *
     * @param key   - map key
     * @param value - map value
     * @param ttl   - seconds time to live for key\value entry.
     *              If <code>0</code> then stores infinitely.
     * @return <code>true</code> if key is a new key in the hash and value was set.
     * <code>false</code> if key already exists in the hash and the value was updated.
     */
    Mono<Boolean> fastPut(K key, V value, long ttl);

    /**
     * Stores value mapped by key with specified time to live and max idle time.
     * Entry expires when specified time to live or max idle time has expired.
     * <p>
     * If the map previously contained a mapping for
     * the key, the old value is replaced by the specified value.
     * <p>
     * as it not returns previous value.
     *
     * @param key         - map key
     * @param value       - map value
     * @param ttl         -seconds time to live for key\value entry.
     *                    If <code>0</code> then time to live doesn't affect entry expiration.
     * @param maxIdleTime - max seconds idle time for key\value entry.
     *                    If <code>0</code> then max idle time doesn't affect entry expiration.
     *                    <p>
     *                    if <code>maxIdleTime</code> and <code>ttl</code> params are equal to <code>0</code>
     *                    then entry stores infinitely.
     * @return <code>true</code> if key is a new key in the hash and value was set.
     * <code>false</code> if key already exists in the hash and the value was updated.
     */
    Mono<Boolean> fastPut(K key, V value, long ttl, long maxIdleTime);

    /**
     * If the specified key is not already associated
     * with a value, associate it with the given value.
     * <p>
     * Stores value mapped by key with specified time to live and max idle time.
     * Entry expires when specified time to live or max idle time has expired.
     * <p>
     * as it not returns previous value.
     *
     * @param key         - map key
     * @param value       - map value
     * @param ttl         - seconds time to live for key\value entry.
     *                    If <code>0</code> then time to live doesn't affect entry expiration.
     * @param maxIdleTime - max seconds idle time for key\value entry.
     *                    If <code>0</code> then max idle time doesn't affect entry expiration.
     *                    <p>
     *                    if <code>maxIdleTime</code> and <code>ttl</code> params are equal to <code>0</code>
     *                    then entry stores infinitely.
     * @return <code>true</code> if key is a new key in the hash and value was set.
     * <code>false</code> if key already exists in the hash
     */
    Mono<Boolean> fastPutIfAbsent(K key, V value, long ttl, long maxIdleTime);

    /**
     * Returns the number of entries in cache.
     * This number can reflects expired entries too
     * due to non realtime cleanup process.
     */
    Mono<Integer> size();

    /**
     * Computes a new mapping for the specified key and its current mapped value.
     *
     * @param key               - map key
     * @param remappingFunction - function to compute a value
     * @return the new value associated with the specified key, or {@code null} if none
     */
    Mono<V> compute(K key, BiFunction<? super K, ? super V, ? extends V> remappingFunction);

    /**
     * Computes a mapping for the specified key if it's not mapped before.
     *
     * @param key             - map key
     * @param mappingFunction - function to compute a value
     * @return current or new computed value associated with
     * the specified key, or {@code null} if the computed value is null
     */
    Mono<V> computeIfAbsent(K key, Function<? super K, ? extends V> mappingFunction);

    /**
     * Computes a mapping for the specified key only if it's already mapped.
     *
     * @param key               - map key
     * @param remappingFunction - function to compute a value
     * @return the new value associated with the specified key, or null if none
     */
    Mono<V> computeIfPresent(K key, BiFunction<? super K, ? super V, ? extends V> remappingFunction);

    /**
     * Returns map slice contained the mappings with defined <code>keys</code>.
     * <p>
     * If map doesn't contain value/values for specified key/keys and {@link MapLoader} is defined
     * then value/values will be loaded in read-through mode.
     * <p>
     * The returned map is <b>NOT</b> backed by the original map.
     *
     * @param keys - map keys
     * @return Map slice
     */
    Mono<Map<K, V>> getAll(Set<K> keys);

    /**
     * Stores map entries specified in <code>map</code> object in batch mode.
     * <p>
     * If {@link MapWriter} is defined then map entries will be stored in write-through mode.
     *
     * @param map mappings to be stored in this map
     * @return void
     */
    Mono<Void> putAll(Map<? extends K, ? extends V> map);


    /**
     * Returns <code>true</code> if this map contains map entry
     * mapped by specified <code>key</code>, otherwise <code>false</code>
     *
     * @param key - map key
     * @return <code>true</code> if this map contains map entry
     * mapped by specified <code>key</code>, otherwise <code>false</code>
     */
    Mono<Boolean> containsKey(Object key);


    /**
     * Removes map entries mapped by specified <code>keys</code>.
     * <p>
     * Works faster than <code>{@link #remove(Object)}</code> but not returning
     * the value.
     * <p>
     * If {@link MapWriter} is defined then <code>keys</code>are deleted in write-through mode.
     *
     * @param keys - map keys
     * @return the number of keys that were removed from the hash, not including specified but non existing keys
     */
    Mono<Long> fastRemove(K... keys);

    /**
     * Removes map entries mapped by specified <code>keys</code>.
     * <p>
     * Works faster than <code>{@link #remove(Object)}</code> but not returning
     * the value.
     * <p>
     * If {@link MapWriter} is defined then <code>keys</code>are deleted in write-through mode.
     *
     * @param keys - map keys
     * @return the number of keys that were removed from the hash, not including specified but non existing keys
     */
    Mono<Boolean> fastRemove(K keys);

    /**
     * Stores the specified <code>value</code> mapped by specified <code>key</code>.
     * <p>
     * Works faster than <code>{@link #put(Object, Object)}</code> but not returning
     * previous value.
     * <p>
     * Returns <code>true</code> if key is a new key in the hash and value was set or
     * <code>false</code> if key already exists in the hash and the value was updated.
     * <p>
     * If {@link MapWriter} is defined then map entry is stored in write-through mode.
     *
     * @param key   - map key
     * @param value - map value
     * @return <code>true</code> if key is a new key in the hash and value was set.
     * <code>false</code> if key already exists in the hash and the value was updated.
     */
    Mono<Boolean> fastPut(K key, V value);

    /**
     * Stores the specified <code>value</code> mapped by specified <code>key</code>
     * only if there is no value with specified<code>key</code> stored before.
     * <p>
     * Returns <code>true</code> if key is a new one in the hash and value was set or
     * <code>false</code> if key already exists in the hash and change hasn't been made.
     * <p>
     * Works faster than <code>{@link #putIfAbsent(Object, Object)}</code> but not returning
     * the previous value associated with <code>key</code>
     * <p>
     * If {@link MapWriter} is defined then new map entry is stored in write-through mode.
     *
     * @param key   - map key
     * @param value - map value
     * @return <code>true</code> if key is a new one in the hash and value was set.
     * <code>false</code> if key already exists in the hash and change hasn't been made.
     */
    Mono<Boolean> fastPutIfAbsent(K key, V value);

    /**
     * Read all keys at once
     *
     * @return keys
     */
    Mono<Set<K>> readAllKeySet();

    /**
     * Read all values at once
     *
     * @return values
     */
    Mono<Collection<V>> readAllValues();

    /**
     * Read all map entries at once
     *
     * @return entries
     */
    Mono<Set<Map.Entry<K, V>>> readAllEntrySet();

    /**
     * Read all map as local instance at once
     *
     * @return map
     */
    Mono<Map<K, V>> readAllMap();

    /**
     * Returns the value mapped by defined <code>key</code> or {@code null} if value is absent.
     * <p>
     * If map doesn't contain value for specified key and {@link MapLoader} is defined
     * then value will be loaded in read-through mode.
     *
     * @param key the key
     * @return the value mapped by defined <code>key</code> or {@code null} if value is absent
     */
    Mono<V> get(K key);

    /**
     * Stores the specified <code>value</code> mapped by specified <code>key</code>.
     * Returns previous value if map entry with specified <code>key</code> already existed.
     * <p>
     * If {@link MapWriter} is defined then map entry is stored in write-through mode.
     *
     * @param key   - map key
     * @param value - map value
     * @return previous associated value
     */
    Mono<V> put(K key, V value);

    /**
     * Removes map entry by specified <code>key</code> and returns value.
     * <p>
     * If {@link MapWriter} is defined then <code>key</code>is deleted in write-through mode.
     *
     * @param key - map key
     * @return deleted value, <code>null</code> if map entry doesn't exist
     */
    Mono<V> remove(K key);


    /**
     * Stores the specified <code>value</code> mapped by specified <code>key</code>
     * only if there is no value with specified<code>key</code> stored before.
     * <p>
     * If {@link MapWriter} is defined then new map entry is stored in write-through mode.
     *
     * @param key   - map key
     * @param value - map value
     * @return <code>null</code> if key is a new one in the hash and value was set.
     * Previous value if key already exists in the hash and change hasn't been made.
     */
    Mono<V> putIfAbsent(K key, V value);


}
