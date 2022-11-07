package com.noname.uol.config.jwt;

import java.util.List;

public interface UserSelecionador<T,ID> {
	public T select(List<T> objetos, ID identificador);
}
