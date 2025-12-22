package com.onetuks.ihub.service.project;

import com.onetuks.ihub.dto.project.AttachmentCreateRequest;
import com.onetuks.ihub.dto.project.AttachmentUpdateRequest;
import com.onetuks.ihub.entity.file.File;
import com.onetuks.ihub.entity.project.Attachment;
import com.onetuks.ihub.entity.project.Project;
import com.onetuks.ihub.entity.user.User;
import com.onetuks.ihub.mapper.AttachmentMapper;
import com.onetuks.ihub.repository.AttachmentJpaRepository;
import com.onetuks.ihub.repository.FileJpaRepository;
import com.onetuks.ihub.repository.ProjectJpaRepository;
import com.onetuks.ihub.repository.UserJpaRepository;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AttachmentService {

  private final AttachmentJpaRepository attachmentJpaRepository;
  private final ProjectJpaRepository projectJpaRepository;
  private final FileJpaRepository fileJpaRepository;
  private final UserJpaRepository userJpaRepository;

  @Transactional
  public Attachment create(AttachmentCreateRequest request) {
    Attachment attachment = new Attachment();
    AttachmentMapper.applyCreate(attachment, request);
    attachment.setProject(findProject(request.projectId()));
    attachment.setFile(findFile(request.fileId()));
    attachment.setAttachedBy(findUser(request.attachedById()));
    return attachmentJpaRepository.save(attachment);
  }

  @Transactional(readOnly = true)
  public Attachment getById(String attachmentId) {
    return findEntity(attachmentId);
  }

  @Transactional(readOnly = true)
  public List<Attachment> getAll() {
    return attachmentJpaRepository.findAll();
  }

  @Transactional
  public Attachment update(String attachmentId, AttachmentUpdateRequest request) {
    Attachment attachment = findEntity(attachmentId);
    AttachmentMapper.applyUpdate(attachment, request);
    if (request.fileId() != null) {
      attachment.setFile(findFile(request.fileId()));
    }
    if (request.attachedById() != null) {
      attachment.setAttachedBy(findUser(request.attachedById()));
    }
    return attachment;
  }

  @Transactional
  public void delete(String attachmentId) {
    Attachment attachment = findEntity(attachmentId);
    attachmentJpaRepository.delete(attachment);
  }

  private Attachment findEntity(String attachmentId) {
    return attachmentJpaRepository.findById(attachmentId)
        .orElseThrow(() -> new EntityNotFoundException("Attachment not found: " + attachmentId));
  }

  private Project findProject(String projectId) {
    return projectJpaRepository.findById(projectId)
        .orElseThrow(() -> new EntityNotFoundException("Project not found: " + projectId));
  }

  private File findFile(String fileId) {
    return fileJpaRepository.findById(fileId)
        .orElseThrow(() -> new EntityNotFoundException("File not found: " + fileId));
  }

  private User findUser(String userId) {
    return userJpaRepository.findById(userId)
        .orElseThrow(() -> new EntityNotFoundException("User not found: " + userId));
  }
}
