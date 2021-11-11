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
public class LongData {
    public final long value;
    public final int sizeProto; // кол-во байт, занимаемых значением в исходном прото-массиве

    public LongData(long value, int sizeProto) {
        this.value = value;
        this.sizeProto = sizeProto;
    }    
}
