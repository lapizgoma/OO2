package negocio;

import java.util.List;

public interface INegocio <T> {
	T traer(Long id);
	List<T> traer();
	Long agregar(T objeto);
	void actualizar(T objeto);
	void eliminar(T objeto);
}
