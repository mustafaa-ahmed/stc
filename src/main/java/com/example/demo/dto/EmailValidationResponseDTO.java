package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmailValidationResponseDTO {
    private Data data;

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Data {
        private String status;
        private String result;
        private int score;
        private boolean regexp;
        private boolean gibberish;
        private boolean disposable;
        private boolean webmail;
        private boolean mxRecords;
        private boolean smtpServer;
        private boolean smtpCheck;
        private boolean acceptAll;
        private boolean block;
    }

    private Meta meta;

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Meta {
        private Params params;
        @Getter
        @Setter
        @AllArgsConstructor
        @NoArgsConstructor
        public static class Params {
            private String email;
        }
    }
}

