/**
 * 
 */
package com.hd.phim.data.adapter;

/**
 * @author hdtua_000
 *
 */
public class CatMovie {
private String nameCat;
private String keyCat;

public CatMovie(String nameCat, String keyCat) {
	this.nameCat = nameCat;
	this.keyCat = keyCat;
}
public String getNameCat() {
	return nameCat;
}
public void setNameCat(String nameCat) {
	this.nameCat = nameCat;
}
public String getKeyCat() {
	return keyCat;
}
public void setKeyCat(String keyCat) {
	this.keyCat = keyCat;
}
@Override
public String toString() {
	// TODO Auto-generated method stub
	return getNameCat();
}


}
