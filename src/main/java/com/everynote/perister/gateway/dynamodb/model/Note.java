package com.everynote.perister.gateway.dynamodb.model;

public class Note {

  private String id;
  private String noteBody;

  private Long createdAt;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getNoteBody() {
    return noteBody;
  }

  public void setNoteBody(String noteBody) {
    this.noteBody = noteBody;
  }

  public Long getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(Long createdAt) {
    this.createdAt = createdAt;
  }
}
