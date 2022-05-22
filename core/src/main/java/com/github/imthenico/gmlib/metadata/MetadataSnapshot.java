package com.github.imthenico.gmlib.metadata;

public class MetadataSnapshot extends SimpleMetadataHolder {

    public MetadataSnapshot(MetadataHolder metadataHolder) {
        metadataHolder.forEach(entry -> set(entry.getKey(), entry.getValue()));
    }
}