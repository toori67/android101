package com.lizzardry.ril.cache.layer;

public interface ILru<K, T> {

    /**
     * 가져오기!
     * @param key
     * @return
     */
    T get(K key);

    /**
     * 추가! Cache object 에 cache count++ 해야한다.
     * @param key
     * @param value
     */
    void put(K key, T value);

    /**
     * 해당 cache layer 가 적용되는 지 (disk 만 exception 으로 걸릴듯)
     * @return
     */
    boolean isEnabled();

    /**
     * 빠르게 contain 을 체크한다
     * 일반적으로는 n layer 를 concurrent 하게 요청해서
     * 먼저 응답이 `정상적` 으로 오면 나머지 task 를 취소해야하지만
     * 굳이?
     *
     * @param key
     * @return
     */
    boolean has(K key);
}
