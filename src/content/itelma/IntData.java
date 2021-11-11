/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package content.itelma;

/**
 *
 * @author kozenkov
 */
public class IntData {
    public final int value;
    public final int sizeProto; // кол-во байт, занимаемых значением в исходном прото-массиве

    public IntData(int value, int sizeProto) {
        this.value = value;
        this.sizeProto = sizeProto;
    }    
}
