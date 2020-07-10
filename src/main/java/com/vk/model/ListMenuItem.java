package com.vk.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ListMenuItem {

    @Builder.Default
    String name = "菜单";

    @Builder.Default
    String path = "DashboardController";

    @Builder.Default
    boolean hidden = false;

    @Builder.Default
    String icon = null;

    @Builder.Default
    List<ListMenuItem> children = new ArrayList<>();

}
