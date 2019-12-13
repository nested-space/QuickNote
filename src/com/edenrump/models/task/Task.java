package com.edenrump.models.task;

import javafx.beans.property.ReadOnlyListWrapper;
import javafx.collections.FXCollections;

import java.util.*;

public class Task {
    /**
     * The database of the task
     */
    private String _id;

    /**
     * The name (i.e. display name) of the task
     * It is not recommended that this is longer than 40 characters
     */
    private String name;

    /**
     * The date the task was created
     */
    private Date created;

    /**
     * The id of the user who created the task.
     */
    private String createdBy_Id;

    /**
     * The id of the user who owns the task
     */
    private String owner_id;

    /**
     * The priority of the task
     */
    private String priority;

    /**
     * The id of the task's parent, if applicable
     */
    private String parentTask_id;

    /**
     * A list of checklists associated with the task
     */
    private List<Checklist> checklists;

    /**
     * A list of attachments associated with the task
     */
    private List<Attachment> attachments;

    /**
     * A list of tags associated with the task
     */
    private List<String> tags;

    /**
     * A list of comments associated with the task
     */
    private List<Comment> comments;

    /**
     * A method to create a Task when all information is available upfront
     *
     * @param _id           the id of the task
     * @param name          the name of the task (recommended limit 40 characters)
     * @param created       the date the task was created
     * @param createdBy_Id  the id of the user who created the task
     * @param owner_id      the id of the user who owns the task
     * @param priority      the priority of the task
     * @param parentTask_id the id of the tasks parent
     * @param checklists    a list of checklists associated with the task
     * @param attachments   a list of attachments associated with the task
     * @param tags          a list of tags associated with the task
     * @param comments      a list of comments associated with the task
     */
    public Task(String _id, String name, Date created, String createdBy_Id, String owner_id, String priority, String parentTask_id, List<Checklist> checklists, List<Attachment> attachments, List<String> tags, List<Comment> comments) {
        this._id = _id;
        this.name = name;
        this.created = created;
        this.createdBy_Id = createdBy_Id;
        this.owner_id = owner_id;
        this.priority = priority;
        this.parentTask_id = parentTask_id;
        this.checklists = checklists;
        this.attachments = attachments;
        this.tags = tags;
        this.comments = comments;
    }

    /**
     * A method to create a Task when all information is available upfront
     *
     * @param name         the name of the task (recommended limit 40 characters)
     * @param created      the date the task was created
     * @param createdBy_Id the id of the user who created the task
     */
    public Task(String name, Date created, String createdBy_Id) {
        this._id = UUID.randomUUID().toString();
        this.name = name;
        this.created = created;
        this.createdBy_Id = createdBy_Id;
        this.owner_id = createdBy_Id;
    }

    /**
     * Method to get the id of the task
     *
     * @return the id of the task
     */
    public String get_id() {
        return _id;
    }

    /**
     * Method to get the name of the task
     *
     * @return the name of the task
     */
    public String getName() {
        return name;
    }

    /**
     * Method to get the date the task was created
     *
     * @return the date the task was created
     */
    public Date getCreated() {
        return created;
    }

    /**
     * Method to get the id of the user who created the task
     *
     * @return the id of the user who created the task
     */
    public String getCreatedBy_Id() {
        return createdBy_Id;
    }

    /**
     * Method to get the id of the owner of the task
     *
     * @return the id of the owner of the task
     */
    public String getOwner_id() {
        return owner_id;
    }

    /**
     * Method to get the priority of the task
     *
     * @return the priority of the task
     */
    public String getPriority() {
        return priority;
    }

    /**
     * Method to get the id of the task's parent (if it has one)
     *
     * @return the id of the task's parent (may be null if no parent exists).
     */
    public String getParentTask_id() {
        return parentTask_id;
    }

    /**
     * Method to get the checklists associated with the task
     *
     * @return the checklists associated with the task. Returns empty list if no checklists associated
     */
    public List<Checklist> getChecklistsReadOnly() {
        return checklists == null ? new ArrayList<>() : new ReadOnlyListWrapper<>(FXCollections.observableArrayList(checklists));
    }

    public List<Checklist> getChecklists() {
        return checklists == null ? new ArrayList<>() : checklists;
    }

    /**
     * Method to get the attachments associated with the task
     *
     * @return the attachments associated with the task. Returns empty list if no attachments associated
     */
    public List<Attachment> getAttachmentsReadOnly() {
        return attachments == null ? new ArrayList<>() : new ReadOnlyListWrapper<>(FXCollections.observableArrayList(attachments));
    }

    /**
     * Method to get the tags associated with the task
     *
     * @return the tags associated with the task. Returns empty list if no tags associated
     */
    public List<String> getTagsReadOnly() {
        return tags == null ? new ArrayList<>() : new ReadOnlyListWrapper<>(FXCollections.observableArrayList(tags));
    }

    /**
     * Method to get the comments associated with the task
     *
     * @return the comments associated with the task. Returns empty list if no comments associated
     */
    public List<Comment> getCommentsReadOnly() {
        return comments == null ? new ArrayList<>() : new ReadOnlyListWrapper<>(FXCollections.observableArrayList(comments));
    }

    /**
     * Method to set the id of the owner of the task
     *
     * @param owner_id the id of the owner of the task
     */
    public void setOwner_id(String owner_id) {
        this.owner_id = owner_id;
    }

    /**
     * Method to set the priority of the task
     *
     * @param priority the priority of the task
     */
    public void setPriority(String priority) {
        this.priority = priority;
    }

    /**
     * Method to set the id of the task's parent
     *
     * @param parentTask_id the id of the parent of the task
     */
    public void setParentTask_id(String parentTask_id) {
        this.parentTask_id = parentTask_id;
    }

    /**
     * Method to set the checklists associated with the task
     *
     * @param checklists the checklists associated with the task
     */
    public void setChecklists(List<Checklist> checklists) {
        this.checklists = checklists;
    }

    /**
     * Method to set the attachments associated with the task
     *
     * @param attachments the attachments associated with the task
     */
    public void setAttachments(List<Attachment> attachments) {
        this.attachments = attachments;
    }

    /**
     * Method to set the tags associated with the task
     *
     * @param tags the tags associated with the task
     */
    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    /**
     * Method to set the comments associated with the task
     *
     * @param comments the comments associated with the task
     */
    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    /**
     * Method to add a comment to the list of comments associated with the task.
     * Supports lazy initialisation of comments
     *
     * @param comment the comment to be associated with the task
     */
    public void addComment(Comment comment) {
        if (comments == null) comments = new ArrayList<>();
        comments.add(comment);
    }

    /**
     * Method to add a attachment to the list of comments associated with the task.
     * Supports lazy initialisation of comments
     *
     * @param attachment the attachment to be associated with the task
     */
    public void addAttachment(Attachment attachment) {
        if (attachments == null) attachments = new ArrayList<>();
        attachments.add(attachment);
    }

    /**
     * Method to add a tag to the list of comments associated with the task.
     * Supports lazy initialisation of comments
     *
     * @param tag the tag to be associated with the task
     */
    public void addTag(String tag) {
        if (tags == null) tags = new ArrayList<>();
        tags.add(tag);
    }

    /**
     * Method to add a checklist to the list of comments associated with the task.
     * Supports lazy initialisation of comments
     *
     * @param checklist the checklist to be associated with the task
     */
    public void addChecklist(Checklist checklist) {
        if (checklists == null) checklists = new ArrayList<>();
        checklists.add(checklist);
    }

    /**
     * Inner class representing attachments, generally a file attached to a task.
     */
    public class Attachment {
        /**
         * The location of the attachment
         */
        private String location;

        /**
         * The filetype of the attachment
         */
        private String type;

        /**
         * Method to create a new attachment using the location url and filetype
         *
         * @param location the location of the file
         * @param type     the filetype
         */
        public Attachment(String location, String type) {
            this.location = location;
            this.type = type;
        }

        /**
         * Method to get the location of the file
         *
         * @return the location of the file
         */
        public String getLocation() {
            return location;
        }

        /**
         * Method to get the filetype
         *
         * @return the type of file
         */
        public String getType() {
            return type;
        }
    }

    /**
     * Inner class representing a checklist
     */
    public class Checklist {

        /**
         * Whether the checklist is complete
         */
        boolean complete;

        /**
         * The list of items in the checklist
         */
        List<ChecklistItem> items;

        /**
         * Method to create a checklist from a defined set of items and a status
         *
         * @param complete whether the checklist is complete
         * @param items    the items in the list
         */
        public Checklist(boolean complete, LinkedList<ChecklistItem> items) {
            this.complete = complete;
            this.items = items;
        }

        /**
         * Method to create a blank checklist with a default completeness state of false
         */
        public Checklist() {
            items = new LinkedList<>();
            complete = false;
        }

        /**
         * Method to get whether the checklist is complete
         *
         * @return whether the checklist is complete
         */
        public boolean isComplete() {
            return complete;
        }

        /**
         * method to get all items in the checklist
         *
         * @return a list of all items in the list
         */
        public List<ChecklistItem> getItems() {
            return items;
        }

        /**
         * Method to get a single item at the specified index from the list
         *
         * @param index the specified index of the item to retrieve
         * @return the item at the specified index.
         */
        public ChecklistItem getItem(int index) {
            if (index > items.size() || index < 0) return null;
            return items.get(index);
        }

    }

    /**
     * Inner class representing a checklist item.
     */
    public class ChecklistItem {
        /**
         * Whether the item is complete
         */
        private boolean done;

        /**
         * The text (name or description) of the item
         */
        private String text;

        /**
         * Method to set the item number
         *
         * @param number the item number
         */
        public void setNumber(int number) {
            this.number = number;
        }

        /**
         * Method to get the item number
         *
         * @return the item number
         */
        public int getNumber() {
            return number;
        }

        private int number;

        /**
         * Method to create a checklist item
         *
         * @param done whether the task is compelete
         * @param text the text of the task
         */
        public ChecklistItem(boolean done, String text) {
            this.done = done;
            this.text = text;
        }

        /**
         * Method to get whether the task has been compeleted
         *
         * @return whether the task has been completed
         */
        public boolean isDone() {
            return done;
        }

        /**
         * Method to get the text of the checklist item, limited to 60 characters
         *
         * @return the text of the item
         */
        public String getText() {
            return text;
        }
    }


    /**
     * Inner class representing a comment on a task
     */
    public class Comment {

        /**
         * The ID of the user who submitted the comment
         */
        private String userId;

        /**
         * The content of the comment
         */
        private String content;
        /**
         * The date the comment was created
         */
        private Date created;

        /**
         * Method to create a comment using defined userId, content and created date
         *
         * @param userId  the ID of the user
         * @param content the content of the comment
         * @param created the date the comment was created
         */
        public Comment(String userId, String content, Date created) {
            this.userId = userId;
            this.content = content;
            this.created = created;
        }

        /**
         * Method to create a new comment, setting the date to the current date
         */
        public Comment() {
            this.created = new Date();
        }

        /**
         * Method to get the ID of the user who made the comment
         *
         * @return the ID of the user who made the comment
         */
        public String getUserId() {
            return userId;
        }

        /**
         * Method to get the content of the comment
         *
         * @return the content of the comment
         */
        public String getContent() {
            return content;
        }

        /**
         * Methd to get the date the comment was created
         *
         * @return the date the comment was created
         */
        public Date getCreated() {
            return created;
        }
    }
}