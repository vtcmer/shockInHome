package com.ztt.stockinhome.libs;

/**
 * Clase para encapsular la implementación
 * para el intercambio de mensajes entre capas
 */

public interface EventBus {

  /**
   * Registro de un evento
   * @param subscriber
     */
  void register(Object subscriber);

  /**
   * Desregistramos el evento
   * @param subscriber
     */
  void unregister(Object subscriber);

  /**
   * Enviamos el evento
   * @param event
     */
  void post(Object event);

}