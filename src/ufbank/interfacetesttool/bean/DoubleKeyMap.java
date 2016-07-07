package ufbank.interfacetesttool.bean;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Ë«¼ümap
 * 
 * @author TonyTang
 *
 */
public class DoubleKeyMap<K, V> {
	private List<K> key;
	private List<V> value;

	public DoubleKeyMap() {
		super();
		this.key = new ArrayList<K>();
		this.value = new ArrayList<V>();
	}

	public boolean put(K key, V value) {
		if (!this.key.contains(key) && !this.value.contains(value)) {
			this.key.add(key);
			this.value.add(value);
			return true;
		}
		return false;
	}

	public boolean removeByKey(K key) {
		if (this.key.contains(key)) {
			int index = this.key.indexOf(key);
			this.key.remove(index);
			this.value.remove(index);
			return true;
		}
		return false;
	}

	public boolean removeByValue(V value) {
		if (this.value.contains(value)) {
			int index = this.value.indexOf(value);
			this.key.remove(index);
			this.value.remove(index);
			return true;
		}
		return false;
	}

	public V getValueByKey(K key) {
		if (this.key.contains(key)) {
			int index = this.key.indexOf(key);
			return this.value.get(index);
		}
		return null;
	}

	public K getKeyByValue(V value) {
		if (this.value.contains(value)) {
			int index = this.value.indexOf(value);
			return this.key.get(index);
		}
		return null;
	}

	public List<K> keyList() {
		return this.key;
	}

	public List<V> valueList() {
		return this.value;
	}
}
