package com.lms.bs.rest.transformer;

import com.lms.bs.rest.model.entity.BookStatus;

public final class BookStatusTransformer {
    public static String getBookStatusForClient(BookStatus bookStatus) {
        return BookStatusDBCode.getBookStatusString(bookStatus);
    }

    public static BookStatus getBookStatusFromClient(String clientStatus) {
        return BookStatusDBCode.getBookStatusFromClientStatus(clientStatus);
    }
    
    private enum BookStatusDBCode {
        AVAILABLE("A", "AVAILABLE"),
        DELETED("D", "DELETED"),
        TEMPORARILY_UNAVAILABLE("T", "TEMP_UNAVL"),
        NOT_AVAILABLE("T", "NOT_AVL");

        String dbCode;
        String clientStatus;

        BookStatusDBCode(String dbCode, String clientStatus) {
            this.dbCode = dbCode;
            this.clientStatus = clientStatus;
        }

        static String getBookStatusString(BookStatus bookStatus) {
            String bookStatusString = null;
            for(BookStatusDBCode statusDBCode : BookStatusDBCode.values()) {
                if(statusDBCode.dbCode.equalsIgnoreCase(bookStatus.getStatusCode())) {
                    bookStatusString = statusDBCode.clientStatus;
                    break;
                }
            }
            return bookStatusString;
        }
        static BookStatus getBookStatusFromClientStatus(String clientStatus) {
            BookStatus bookStatus = new BookStatus();
            for(BookStatusDBCode statusDBCode : BookStatusDBCode.values()) {
                if(clientStatus.equalsIgnoreCase(statusDBCode.clientStatus)) {
                    bookStatus.setStatusCode(statusDBCode.dbCode);
                    break;
                }
            }
            return bookStatus;
        }
    }

}
