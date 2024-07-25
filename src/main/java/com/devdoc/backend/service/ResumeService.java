package com.devdoc.backend.service;

import com.devdoc.backend.dto.*;
import com.devdoc.backend.model.*;
import com.devdoc.backend.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ResumeService {

    private final ResumeRepository resumeRepository;

    private final UserRepository userRepository;

    public ResumeService(ResumeRepository resumeRepository, UserRepository userRepository) {
        this.resumeRepository = resumeRepository;
        this.userRepository = userRepository;
    }

    // 이력서 소유자 확인
    public boolean isOwner(String userId, int resumeId) {
        Optional<Resume> resume = resumeRepository.findById(resumeId);
        return resume.isPresent() && resume.get().getUser().getId().equals(userId);
    }


    // 이력서 저장
    @Transactional
    public void saveResume(int resumeId, ResumeDTO resumeDTO) {
        Optional<Resume> optionalResume = resumeRepository.findById(resumeId);
        if (optionalResume.isPresent()) {
            Resume resume = optionalResume.get();
            resume.setTitle(resumeDTO.getTitle());

            List<Language> languages = resumeDTO.getLanguages().stream()
                    .map(languageDTO -> new Language(languageDTO.getId(), languageDTO.getLanguage(), languageDTO.getTestName(), languageDTO.getScore(), languageDTO.getDate(), resume))
                    .collect(Collectors.toList());
            resume.setLanguages(languages);

            List<Award> awards = resumeDTO.getAwards().stream()
                    .map(awardDTO -> new Award(awardDTO.getId(), awardDTO.getAwardName(), awardDTO.getAwardingInstitution(), awardDTO.getDate(), awardDTO.getDescription(), resume))
                    .collect(Collectors.toList());
            resume.setAwards(awards);

            List<Certificate> certificates = resumeDTO.getCertificates().stream()
                    .map(certificateDTO -> new Certificate(certificateDTO.getId(), certificateDTO.getCertificateName(), certificateDTO.getIssuer(), certificateDTO.getIssueDate(), resume))
                    .collect(Collectors.toList());
            resume.setCertificates(certificates);

            List<Skill> skills = resumeDTO.getSkills().stream()
                    .map(skillDTO -> new Skill(skillDTO.getId(), skillDTO.getTechStack(), skillDTO.getDescription(), resume))
                    .collect(Collectors.toList());
            resume.setSkills(skills);

            List<Career> careers = resumeDTO.getCareers().stream()
                    .map(careerDTO -> new Career(careerDTO.getId(), careerDTO.getCompany(), careerDTO.getDepartment(), careerDTO.getStartDate(), careerDTO.getEndDate(), careerDTO.getIsCurrent(), careerDTO.getTechStack(), careerDTO.getDescription(), resume))
                    .collect(Collectors.toList());
            resume.setCareers(careers);

            List<Project> projects = resumeDTO.getProjects().stream()
                    .map(projectDTO -> new Project(projectDTO.getId(), projectDTO.getTitle(), projectDTO.getStartDate(), projectDTO.getEndDate(), projectDTO.getIsCurrent(), projectDTO.getIntro(), projectDTO.getTechStack(), projectDTO.getDescription(), resume))
                    .collect(Collectors.toList());
            resume.setProjects(projects);

            List<Activity> activities = resumeDTO.getActivities().stream()
                    .map(activityDTO -> new Activity(activityDTO.getId(), activityDTO.getActivityName(), activityDTO.getOrganizationName(), activityDTO.getStartDate(), activityDTO.getEndDate(), activityDTO.getIsCurrent(), resume))
                    .collect(Collectors.toList());
            resume.setActivities(activities);

            List<Training> trainings = resumeDTO.getTrainings().stream()
                    .map(trainingDTO -> new Training(trainingDTO.getId(), trainingDTO.getCourseName(), trainingDTO.getInstitution(), trainingDTO.getStartDate(), trainingDTO.getEndDate(), trainingDTO.getIsCurrent(), resume))
                    .collect(Collectors.toList());
            resume.setTrainings(trainings);

            AboutMeDTO aboutMeDTO = resumeDTO.getAboutMe();
            AboutMe aboutMe = new AboutMe(aboutMeDTO.getId(), aboutMeDTO.getPhoto(), aboutMeDTO.getName(), aboutMeDTO.getBirthday(), aboutMeDTO.getEmail(), aboutMeDTO.getGithub(), aboutMeDTO.getPhoneNumber(), aboutMeDTO.getBlog(), aboutMeDTO.getIntroduction(), resume);
            resume.setAboutMe(aboutMe);

            List<Education> educations = resumeDTO.getEducations().stream()
                    .map(educationDTO -> new Education(educationDTO.getId(), educationDTO.getSchoolName(), educationDTO.getMajor(), educationDTO.getStartDate(), educationDTO.getEndDate(), educationDTO.getStatus(), educationDTO.getEducationType(), resume))
                    .collect(Collectors.toList());
            resume.setEducations(educations);

            resumeRepository.save(resume);
        }
    }

    // 특정 이력서 조회
    public ResumeDTO getResumeByResumeId(int resumeId) {
        Optional<Resume> optionalResume = resumeRepository.findById(resumeId);
        if (optionalResume.isPresent()) {
            Resume resume = optionalResume.get();

            List<LanguageDTO> languageDTOs = resume.getLanguages().stream()
                    .map(language -> new LanguageDTO(language.getId(), language.getLanguage(), language.getTestName(), language.getScore(), language.getDate()))
                    .collect(Collectors.toList());

            List<AwardDTO> awardDTOs = resume.getAwards().stream()
                    .map(award -> new AwardDTO(award.getId(), award.getAwardName(), award.getAwardingInstitution(), award.getDate(), award.getDescription()))
                    .collect(Collectors.toList());

            List<CertificateDTO> certificateDTOs = resume.getCertificates().stream()
                    .map(certificate -> new CertificateDTO(certificate.getId(), certificate.getCertificateName(), certificate.getIssuer(), certificate.getIssueDate()))
                    .collect(Collectors.toList());

            List<SkillDTO> skillDTOs = resume.getSkills().stream()
                    .map(skill -> new SkillDTO(skill.getId(), skill.getTechStack(), skill.getDescription()))
                    .collect(Collectors.toList());

            List<CareerDTO> careerDTOs = resume.getCareers().stream()
                    .map(career -> new CareerDTO(career.getId(), career.getCompany(), career.getDepartment(), career.getStartDate(), career.getEndDate(), career.getIsCurrent(), career.getTechStack(), career.getDescription()))
                    .collect(Collectors.toList());

            List<ProjectDTO> projectDTOs = resume.getProjects().stream()
                    .map(project -> new ProjectDTO(project.getId(), project.getTitle(), project.getStartDate(), project.getEndDate(), project.getIsCurrent(), project.getIntro(), project.getTechStack(), project.getDescription()))
                    .collect(Collectors.toList());

            List<ActivityDTO> activityDTOS = resume.getActivities().stream()
                    .map(activity -> new ActivityDTO(activity.getId(), activity.getActivityName(), activity.getOrganizationName(), activity.getStartDate(), activity.getEndDate(), activity.getIsCurrent()))
                    .collect(Collectors.toList());

            List<TrainingDTO> trainingDTOS = resume.getTrainings().stream()
                    .map(training -> new TrainingDTO(training.getId(), training.getCourseName(), training.getInstitution(), training.getStartDate(), training.getEndDate(), training.getIsCurrent()))
                    .collect(Collectors.toList());

            AboutMeDTO aboutMeDTO = new AboutMeDTO(resume.getAboutMe().getId(), resume.getAboutMe().getPhoto(), resume.getAboutMe().getName(), resume.getAboutMe().getBirthday(), resume.getAboutMe().getEmail(), resume.getAboutMe().getGithub(), resume.getAboutMe().getPhoneNumber(), resume.getAboutMe().getBlog(), resume.getAboutMe().getIntroduction());

            List<EducationDTO> educationDTOS = resume.getEducations().stream()
                    .map(education -> new EducationDTO(education.getId(), education.getSchoolName(), education.getMajor(), education.getStartDate(), education.getEndDate(), education.getStatus(), education.getEducationType()))
                    .collect(Collectors.toList());

            return new ResumeDTO(resume.getId(), resume.getTitle(), resume.getCreatedAt(), languageDTOs, awardDTOs, certificateDTOs, skillDTOs, careerDTOs, projectDTOs, activityDTOS, trainingDTOS, aboutMeDTO, educationDTOS);
        }
        return null;
    }


    // 사용자의 모든 이력서 조회
    public List<ResumeDTO> getAllResumesByUser(String userId) {
        List<Resume> resumes = resumeRepository.findByUserIdWithAboutMe(userId);
        return resumes.stream().map(this::convertResumeToDTO).collect(Collectors.toList());
    }

    // Resume를 DTO로 변환 (AboutMe 제외)
    private ResumeDTO convertResumeToDTO(Resume resume) {
        return new ResumeDTO(
                resume.getId(),
                resume.getTitle(),
                resume.getCreatedAt(),
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null
        );
    }

    // 새로운 이력서 생성
    @Transactional
    public Resume createResume(String title, String userId) {
        Optional<UserEntity> optionalUser = userRepository.findById(userId);
        if (optionalUser.isPresent()) {
            UserEntity user = optionalUser.get();
            Resume resume = new Resume();
            resume.setTitle(title);
            resume.setUser(user);
            resume = resumeRepository.save(resume);
            return resume;
        } else {
            throw new RuntimeException("User not found");
        }
    }

    // 이력서 삭제
    @Transactional
    public void deleteResumeByResumeId(int resumeId) {
        Optional<Resume> optionalResume = resumeRepository.findById(resumeId);
        optionalResume.ifPresent(resumeRepository::delete);
    }

    // 이력서 제목 저장
    @Transactional
    public ResumeDTO saveResumeTitleByResumeId(int resumeId, String newTitle) {
        Optional<Resume> optionalResume = resumeRepository.findById(resumeId);
        if (optionalResume.isPresent()) {
            Resume resume = optionalResume.get();
            resume.setTitle(newTitle);
            resumeRepository.save(resume);
            return new ResumeDTO(resume.getId(), resume.getTitle(), resume.getCreatedAt(), null, null, null, null, null, null, null, null, null, null);
        }
        return null;
    }
}