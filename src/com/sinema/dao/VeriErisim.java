// Dosya: com/sinema/dao/VeriErisim.java
package com.sinema.dao;

import java.util.List;

public interface VeriErisim<T> {
    boolean kaydet(T nesne);
    boolean guncelle(T nesne);
    boolean sil(int id);
    T idIleBul(int id);
    List<T> tumunuListele();
}