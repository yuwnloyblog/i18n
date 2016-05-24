package com.yuwnloy.i18n.dataformatters.config;
import java.util.ArrayList;
import java.util.List;
/**
 * This class define an logic to let caller can return data 
 * according to a specify sequence.
 * 
 * @author xiaoguang.gao
 *
 * @param <T>
 */
public class GetterSeries<T> {
	/**
	 * Define the way to return data.
	 * @author xiaoguang.gao
	 *
	 * @param <T>
	 */
    public static interface Getter<T> {
        public T getResult();
    }
    
    private List<Getter<T>> getterList = new ArrayList<Getter<T>>();
    /**
     * Add a data getter into list. 
     * @param getter
     */
    public void addGetter(Getter<T> getter) {
        this.getterList.add(getter);
    }
    /**
     * Get the data getter list. 
     * @return
     */
    public List<Getter<T>> getGetterList() {
        return this.getterList;
    }
    /**
     * clean the data getter list.
     */
    public void clearGetterList() {
        this.getterList.clear();
    }
    /**
     * As the sequence of data getter list to return the data.
     * @return
     */
    public T getFinalResult() {
        for (GetterSeries.Getter<T> getter : this.getGetterList()) {
            T ret = getter.getResult();
            if (ret != null)
                return ret;
        }
        return null;
    }

}

