// Protocol Buffers - Google's data interchange format
// Copyright 2008 Google Inc.  All rights reserved.
// https://developers.google.com/protocol-buffers/
//
// Redistribution and use in source and binary forms, with or without
// modification, are permitted provided that the following conditions are
// met:
//
//     * Redistributions of source code must retain the above copyright
// notice, this list of conditions and the following disclaimer.
//     * Redistributions in binary form must reproduce the above
// copyright notice, this list of conditions and the following disclaimer
// in the documentation and/or other materials provided with the
// distribution.
//     * Neither the name of Google Inc. nor the names of its
// contributors may be used to endorse or promote products derived from
// this software without specific prior written permission.
//
// THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
// "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
// LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
// A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT
// OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
// SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
// LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
// DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
// THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
// (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
// OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.

package content.com.itelma.proto3;

import java.io.IOException;

/**
 * Выдается, когда анализируемое протокольное сообщение каким-либо образом недействительно, например оно содержит
 * отрицательную длину массива
 *
 * Измененный класс
 * Оригинал класса (com/google/protobuf/InvalidProtocolBufferException.java) v3.19.1
 */
public class InvalidProtocolBufferException extends IOException {
//  private static final long serialVersionUID = -1616151763072450476L;
//  private MessageLite unfinishedMessage = null; ПОКА НЕ ТРЕБУЕТСЯ
  private boolean wasThrownFromInputStream;

  public InvalidProtocolBufferException(final String description) {
    super(description);
  }

  public InvalidProtocolBufferException(IOException e) {
    super(e.getMessage(), e);
  }

  public InvalidProtocolBufferException(final String description, IOException e) {
    super(description, e);
  }

  /**
   Прикрепляет незавершенное сообщение к исключению для поддержки анализа методом максимального усилия в интерфейс.
   * ПОКА НЕ ТРЕБУЕТСЯ
   */
//  public InvalidProtocolBufferException setUnfinishedMessage(MessageLite unfinishedMessage) {
//    this.unfinishedMessage = unfinishedMessage;
//    return this;
//  }
//
//  /**
//   * Returns the unfinished message attached to the exception, or null if no message is attached.
//   */
//  public MessageLite getUnfinishedMessage() {
//    return unfinishedMessage;
//  }

  /** Set by CodedInputStream */
  void setThrownFromInputStream() {
    /*
     Эта запись может быть быстрой, если одно и то же исключение сохраняется, а затем генерируется несколькими настраиваемыми
     InputStreams в разных потоках. Но поскольку он всегда движется только от false-> true, нет
     проблема. Поток, проверяющий это условие после перехвата этого исключения от делегата
     поток CodedInputStream гарантированно всегда соблюдает истину, потому что запись в том же
     thread устанавливает значение, когда исключение покинуло делегата. Нить, проверяющая то же самое
     условие с исключением, созданным CodedInputStream, всегда будет видеть false,
     потому что исключение не было представлено никакому коду, который мог бы опубликовать его в других потоках
     и вызвать запись.
     */
    wasThrownFromInputStream = true;
  }

  /**
   * Позволяет коду улавливать исключение IOException из CodedInputStream, чтобы определить,
   * был ли этот экземпляр сгенерироваy делегатом InputStream, а не напрямую в результате
   * сбоя синтаксического анализа.
   */
  boolean getThrownFromInputStream() {
    return wasThrownFromInputStream;
  }

  /**
   * Unwraps the underlying {@link IOException} if this exception was caused by an I/O problem.
   * Otherwise, returns {@code this}.
   */
  public IOException unwrapIOException() {
    return getCause() instanceof IOException ? (IOException) getCause() : this;
  }

  // ПОКА НЕ ТРЕБУЕТСЯ
//  static InvalidProtocolBufferException truncatedMessage() {
//    return new InvalidProtocolBufferException(
//        "While parsing a protocol message, the input ended unexpectedly "
//            + "in the middle of a field.  This could mean either that the "
//            + "input has been truncated or that an embedded message "
//            + "misreported its own length.");
//  }

  /**
   * CodedInputStream обнаружил встроенную строку или сообщение, которое, как утверждается, имеет отрицательный размер.
   */
  static InvalidProtocolBufferException negativeSize() {
    return new InvalidProtocolBufferException(
        "CodedInputStream encountered an embedded string or message "
            + "which claimed to have negative size.");
  }

  /**
   * CodedInputStream обнаружил искаженный varint.
   */
  static InvalidProtocolBufferException malformedVarint() {
    return new InvalidProtocolBufferException("CodedInputStream encountered a malformed varint.");
  }

  /**
   * Сообщение протокола содержит недопустимый тег (ноль).
   */
  static InvalidProtocolBufferException invalidTag() {
    return new InvalidProtocolBufferException("Protocol message contained an invalid tag (zero).");
  }

  /**
   * Тег конечной группы сообщения протокола не соответствует ожидаемому тегу.
   */
  static InvalidProtocolBufferException invalidEndTag() {
    return new InvalidProtocolBufferException(
        "Protocol message end-group tag did not match expected tag.");
  }

  /**
   * Тег сообщения протокола имеет недопустимый тип провода.
   */
  static InvalidWireTypeException invalidWireType() {
    return new InvalidWireTypeException("Protocol message tag had invalid wire type.");
  }

  /** Исключение, указывающее, что для поля обнаружен неожиданный тип провода.  */
//  @ExperimentalApi
  public static class InvalidWireTypeException extends InvalidProtocolBufferException {
    private static final long serialVersionUID = 3283890091615336259L;

    public InvalidWireTypeException(String description) {
      super(description);
    }
  }

  /**
   * Сообщение протокола имело слишком много уровней вложенности. Может быть злонамеренным.
   * Используйте CodedInputStream.setRecursionLimit, чтобы увеличить предел глубины.
   */
  static InvalidProtocolBufferException recursionLimitExceeded() {
    return new InvalidProtocolBufferException(
        "Protocol message had too many levels of nesting.  May be malicious.  "
            + "Use CodedInputStream.setRecursionLimit() to increase the depth limit.");
  }

  /**
   * Сообщение протокола было слишком большим. Может быть злонамеренным.
   * Используйте CodedInputStream.setSizeLimit, чтобы увеличить предел размера.
   */
  static InvalidProtocolBufferException sizeLimitExceeded() {
    return new InvalidProtocolBufferException(
        "Protocol message was too large.  May be malicious.  "
            + "Use CodedInputStream.setSizeLimit() to increase the size limit.");
  }

  /**
   * Не удалось разобрать сообщение
   */
  static InvalidProtocolBufferException parseFailure() {
    return new InvalidProtocolBufferException("Failed to parse the message.");
  }

  /**
   * Сообщение протокола имеет недопустимый UTF-8
   */
  static InvalidProtocolBufferException invalidUtf8() {
    return new InvalidProtocolBufferException("Protocol message had invalid UTF-8.");
  }
}
