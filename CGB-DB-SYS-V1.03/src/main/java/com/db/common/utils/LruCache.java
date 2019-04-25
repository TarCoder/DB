package com.db.common.utils;

import java.io.Serializable;
import java.util.LinkedHashMap;

public class LruCache<K,V> implements Serializable {
	
	private static final long serialVersionUID = -8950429078217820192L;
	private int cap;
	private LinkedHashMap<K, V> map;

	@SuppressWarnings({ "serial", "rawtypes", "unchecked" })
	public LruCache(int cap) {
		this.cap = cap;
		int maxCap = (int)Math.ceil(cap/0.75f)+1;
		this.map = new LinkedHashMap(maxCap,0.75f,true){
			@Override
			protected boolean removeEldestEntry(java.util.Map.Entry eldest) {
				return size()>cap;
			}
		};
	}
	
	@Override
	public String toString() {
		return "LruCache [map=" + map + "]";
	}

	public void put(K key,V value){
		map.put(key, value);
	}
	
	public V get(K key){
		return map.get(key);
	}
	
	public static void main(String[] args) {
		LruCache<String,Integer> lru = new LruCache<>(3);
		lru.put("A",100);
		lru.put("B",200);
		lru.put("C",300);
		lru.get("A");
		lru.put("D",400);
		System.out.println(lru);
	}
}











