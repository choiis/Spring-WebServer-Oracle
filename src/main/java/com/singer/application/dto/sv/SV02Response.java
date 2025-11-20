package com.singer.application.dto.sv;

public record SV02Response(
        int idx,
        String content,
        int voted
) {

    public int getIdx() {
        return idx();
    }

    public String getContent() {
        return content();
    }

    public int getVoted() {
        return voted();
    }

}
