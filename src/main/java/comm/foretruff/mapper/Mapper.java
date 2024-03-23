package comm.foretruff.mapper;

public interface Mapper<F, T> {

    T mapFrom(F object);
}
